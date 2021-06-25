/*
 * (c) Kitodo. Key to digital objects e. V. <contact@kitodo.org>
 *
 * This file is part of the Kitodo project.
 *
 * It is licensed under GNU General Public License version 3 or later.
 *
 * For the full copyright and license information, please read the
 * GPL3-License.txt file that was distributed with this source code.
 */

package org.kitodo.production.forms.createprocess;

import com.sun.jersey.api.NotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kitodo.api.dataeditor.rulesetmanagement.RulesetManagementInterface;
import org.kitodo.api.dataformat.Workpiece;
import org.kitodo.config.ConfigCore;
import org.kitodo.config.enums.ParameterCore;
import org.kitodo.data.database.beans.Process;
import org.kitodo.data.database.beans.Project;
import org.kitodo.data.database.beans.Template;
import org.kitodo.data.exceptions.DataException;
import org.kitodo.exceptions.InvalidMetadataValueException;
import org.kitodo.exceptions.NoSuchMetadataFieldException;
import org.kitodo.exceptions.ProcessGenerationException;
import org.kitodo.exceptions.RulesetNotFoundException;
import org.kitodo.production.enums.ObjectType;
import org.kitodo.production.forms.BaseForm;
import org.kitodo.production.helper.Helper;
import org.kitodo.production.helper.TempProcess;
import org.kitodo.production.interfaces.RulesetSetupInterface;
import org.kitodo.production.metadata.MetadataEditor;
import org.kitodo.production.process.ProcessGenerator;
import org.kitodo.production.process.ProcessValidator;
import org.kitodo.production.services.ServiceManager;
import org.kitodo.production.services.data.ImportService;
import org.kitodo.production.services.data.ProcessService;
import org.primefaces.PrimeFaces;

@Named("CreateProcessForm")
@ViewScoped
public class CreateProcessForm extends BaseForm implements RulesetSetupInterface {

    private static final Logger logger = LogManager.getLogger(CreateProcessForm.class);

    private final ImportTab importTab = new ImportTab(this);
    private final ProcessDataTab processDataTab = new ProcessDataTab(this);
    private final ProcessMetadataTab processMetadataTab = new ProcessMetadataTab(this);
    private final SearchTab searchTab = new SearchTab(this);
    private final TitleRecordLinkTab titleRecordLinkTab = new TitleRecordLinkTab(this);

    private RulesetManagementInterface rulesetManagementInterface;
    private List<Locale.LanguageRange> priorityList;
    private String acquisitionStage = "create";
    private Project project;
    private Template template;
    private LinkedList<TempProcess> processes = new LinkedList<>();
    private LinkedList<TempProcess> childProcesses = new LinkedList<>();
    private final String processListPath = MessageFormat.format(REDIRECT_PATH, "processes");
    private String referringView = "";

    /**
     * Returns the ruleset management to access the ruleset.
     *
     * @return the ruleset
     */
    @Override
    public RulesetManagementInterface getRuleset() {
        return rulesetManagementInterface;
    }

    /**
     * Update ruleset and docType.
     * @param rulesetFileName as String
     * @throws RulesetNotFoundException thrown if ruleset could not be found
     */
    public void updateRulesetAndDocType(String rulesetFileName) throws RulesetNotFoundException {
        setRulesetManagementInterface(rulesetFileName);
        processDataTab.setAllDocTypes(getAllRulesetDivisions());
    }

    private void setRulesetManagementInterface(String rulesetFileName) throws RulesetNotFoundException {
        try {
            this.rulesetManagementInterface = openRulesetFile(rulesetFileName);
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
    }

    private List<SelectItem> getAllRulesetDivisions() {
        List<SelectItem> allDocTypes = rulesetManagementInterface
                .getStructuralElements(priorityList).entrySet()
                .stream().map(entry -> new SelectItem(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
        if (allDocTypes.isEmpty()) {
            Helper.setErrorMessage("errorLoadingDocTypes");
        }
        return allDocTypes;
    }

    /**
     * Returns the current acquisition stage to adapt the displaying of fields
     * accordingly.
     *
     * @return the current acquisition stage
     */
    @Override
    public String getAcquisitionStage() {
        return acquisitionStage;
    }

    /**
     * Returns the language preference list of the editing user to display
     * labels and options in the user-preferred language.
     *
     * @return the language preference list
     */
    @Override
    public List<Locale.LanguageRange> getPriorityList() {
        return priorityList;
    }

    /**
     * Get importTab.
     *
     * @return value of importTab
     */
    public ImportTab getImportTab() {
        return importTab;
    }

    /**
     * Get processDataTab.
     *
     * @return value of processDataTab
     */
    public ProcessDataTab getProcessDataTab() {
        return processDataTab;
    }

    /**
     * Get processMetadataTab.
     *
     * @return value of processMetadataTab
     */
    public ProcessMetadataTab getProcessMetadataTab() {
        return processMetadataTab;
    }

    /**
     * Get searchTab.
     *
     * @return value of searchTab
     */
    public SearchTab getSearchTab() {
        return searchTab;
    }

    /**
     * Get titleRecordLinkTab.
     *
     * @return value of titleRecordLinkTab
     */
    public TitleRecordLinkTab getTitleRecordLinkTab() {
        return titleRecordLinkTab;
    }

    /**
     * Get newProcesses.
     *
     * @return value of newProcesses
     */
    public List<TempProcess> getProcesses() {
        return processes;
    }

    /**
     * Set newProcesses.
     *
     * @param processes as java.util.List of Process
     */
    public void setProcesses(LinkedList<TempProcess> processes) {
        this.processes = processes;
    }

    /**
     * Get child processes.
     *
     * @return childProcesses
     */
    public List<TempProcess> getChildProcesses() {
        return this.childProcesses;
    }

    /**
     * Set childProcesses.
     *
     * @param childProcesses as java.util.LinkedList of TempProcess
     */
    public void setChildProcesses(LinkedList<TempProcess> childProcesses) {
        this.childProcesses = childProcesses;
    }

    /**
     * Get the main Process that want to be created.
     *
     * @return value of first element in newProcesses
     */
    public Process getMainProcess() {
        if (processes.isEmpty()) {
            throw new NotFoundException("Process list is empty!");
        }
        return processes.get(0).getProcess();
    }

    /**
     * Get template.
     *
     * @return value of template
     */
    public Template getTemplate() {
        return template;
    }

    /**
     * Set template.
     *
     * @param template as org.kitodo.data.database.beans.Template
     */
    public void setTemplate(Template template) {
        this.template = template;
    }

    /**
     * Get project.
     *
     * @return value of project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Set project.
     *
     * @param project as org.kitodo.data.database.beans.Project
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * Create the process and save the metadata.
     */
    public String createNewProcess() {
        if (!canCreateProcess()) {
            return this.stayOnCurrentPage;
        }
        try {
            createProcessHierarchy();
            if (Objects.nonNull(PrimeFaces.current()) && Objects.nonNull(FacesContext.getCurrentInstance())) {
                PrimeFaces.current().executeScript("PF('sticky-notifications').renderMessage({'summary':'"
                        + Helper.getTranslation("processSaving") + "','detail':'"
                        + Helper.getTranslation( "youWillBeRedirected") + "','severity':'info'});");
            }
            return processListPath;
        } catch (DataException e) {
            Helper.setErrorMessage("errorSaving", new Object[] {ObjectType.PROCESS.getTranslationSingular() },
                    logger, e);
        } catch (IOException | ProcessGenerationException e) {
            logger.error(e.getLocalizedMessage());
        }
        return this.stayOnCurrentPage;
    }

    /**
     * Create new Process and reload current view to add another Process.
     *
     * @return path to reload current view
     */
    public String createNewProcessAndContinue() {
        if (!canCreateProcess()) {
            return this.stayOnCurrentPage;
        }
        createNewProcess();
        return FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath()
                + "?referrer=" + referringView
                + "&templateId=" + template.getId()
                + "&projectId=" + project.getId()
                + "&faces-redirect=true";
    }

    private boolean canCreateProcess() {
        if (Objects.nonNull(titleRecordLinkTab.getTitleRecordProcess())
                && (Objects.isNull(titleRecordLinkTab.getSelectedInsertionPosition())
                        || titleRecordLinkTab.getSelectedInsertionPosition().isEmpty())) {
            Helper.setErrorMessage("createProcessForm.createNewProcess.noInsertionPositionSelected");
            return false;
        }
        return true;
    }

    /**
     * Prepare new process which will be created.
     *
     * @param templateId
     *            id of template to query from database
     * @param projectId
     *            id of project to query from database
     * @param referringView
     *            view the user was coming from
     */
    public void prepareProcess(int templateId, int projectId, String referringView) {
        this.referringView = referringView;
        ProcessGenerator processGenerator = new ProcessGenerator();
        try {
            boolean generated = processGenerator.generateProcess(templateId, projectId);
            if (generated) {
                processes = new LinkedList<>(Collections.singletonList(new TempProcess(
                        processGenerator.getGeneratedProcess(), new Workpiece())));
                project = processGenerator.getProject();
                template = processGenerator.getTemplate();
                updateRulesetAndDocType(getMainProcess().getRuleset().getFile());
                processDataTab.prepare();
            }
        } catch (ProcessGenerationException | RulesetNotFoundException e) {
            Helper.setErrorMessage(e.getLocalizedMessage(), logger, e);
        }
    }

    /**
     * Create process hierarchy.
     */
    private void createProcessHierarchy() throws DataException, ProcessGenerationException, IOException {
        // discard all processes in hierarchy except the first if parent process in title record link tab is selected!
        if (this.processes.size() > 1
                && Objects.nonNull(this.titleRecordLinkTab.getTitleRecordProcess())
                && Objects.nonNull(this.titleRecordLinkTab.getSelectedInsertionPosition())
                && !this.titleRecordLinkTab.getSelectedInsertionPosition().isEmpty()) {
            this.processes = new LinkedList<>(Collections.singletonList(this.processes.get(0)));
        }
        processAncestors();
        processChildren();
        // main process and it's ancestors need to be saved so they have IDs before creating their process directories
        ServiceManager.getProcessService().save(getMainProcess());
        if (!createProcessesLocation(this.processes)) {
            throw new IOException("Unable to create directories for process hierarchy!");
        }

        if (this.importTab.isImportChildren() && !createProcessesLocation(this.childProcesses)) {
            throw new IOException("Unable to create directories for child processes!");
        }

        saveProcessHierarchyMetadata();

        // TODO: do the same 'ensureNonEmptyTitles' for child processes?
        if (ImportService.ensureNonEmptyTitles(this.processes)) {
            // saving the main process automatically saves it's parent and ancestor processes as well!
            ServiceManager.getProcessService().save(getMainProcess());
        }

        // add links between child processes and main process
        for (int i = 0; i < childProcesses.size(); i++) {
            Process childProcess = childProcesses.get(i).getProcess();
            MetadataEditor.addLink(getMainProcess(), String.valueOf(i), childProcess.getId());
            ServiceManager.getProcessService().save(childProcess);
        }

        // if a process is selected in 'TitleRecordLinkTab' link it as parent with the first process in the list
        if (this.processes.size() > 0 && Objects.nonNull(titleRecordLinkTab.getTitleRecordProcess())) {
            MetadataEditor.addLink(titleRecordLinkTab.getTitleRecordProcess(),
                titleRecordLinkTab.getSelectedInsertionPosition(), this.processes.get(0).getProcess().getId());
            ProcessService.setParentRelations(titleRecordLinkTab.getTitleRecordProcess(),
                processes.get(0).getProcess());
            String summary = Helper.getTranslation("newProcess.catalogueSearch.linkedToExistingProcessSummary");
            String detail = Helper.getTranslation("newProcess.catalogueSearch.linkedToExistingProcessDetail",
                Collections.singletonList(titleRecordLinkTab.getTitleRecordProcess().getTitle()));
            importTab.showGrowlMessage(summary, detail);
        } else {
            // add links between consecutive processes in list
            for (int i = 0; i < this.processes.size() - 1; i++) {
                TempProcess tempProcess = this.processes.get(i);
                MetadataEditor.addLink(this.processes.get(i + 1).getProcess(), "0", tempProcess.getProcess().getId());
            }
        }
    }

    private void processChildren() {
        // set parent relations between main process and its imported child processes!
        try {
            ImportService.processProcessChildren(getMainProcess(), this.childProcesses, template,
                    rulesetManagementInterface, acquisitionStage, priorityList);
        } catch (DataException | InvalidMetadataValueException | NoSuchMetadataFieldException
                | ProcessGenerationException e) {
            Helper.setErrorMessage("Unable to attach child documents to process: " + e.getMessage());
        }
    }

    private void processAncestors() throws ProcessGenerationException {
        for (TempProcess tempProcess : this.processes) {
            Process process = tempProcess.getProcess();
            // set parent relations between all consecutive process pairs
            int index = this.processes.indexOf(tempProcess);
            if (index < this.processes.size() - 1) {
                ProcessService.setParentRelations(this.processes.get(index + 1).getProcess(), process);
            }
            if (index == 0) {
                List<ProcessDetail> processDetails = processMetadataTab.getProcessDetailsElements();
                process.setSortHelperImages(processDataTab.getGuessedImages());
                // FIXME: this always triggers 'processTitleAlreadyInUse' now, because the process has already been saved
                //  with this title in ImportService.processProcessChildren (line 884)!
                if (!ProcessValidator.isContentValid(process.getTitle(), processDetails, true)) {
                    throw new ProcessGenerationException("Error creating process hierarchy: invalid process content!");
                }
                processMetadataTab.preserve();
                ImportService.addProperties(process, template, processDetails, processDataTab.getDocType(),
                        processDataTab.getTiffHeaderImageDescription());
                ImportService.updateTasks(process);
            } else if (Objects.nonNull(tempProcess.getMetadataNodes())) {
                try {
                    ImportService.processTempProcess(tempProcess, template, rulesetManagementInterface,
                            acquisitionStage, priorityList);
                } catch (InvalidMetadataValueException | NoSuchMetadataFieldException e) {
                    throw new ProcessGenerationException("Error creating process hierarchy: invalid metadata found!");
                }
            }
        }
    }

    private void saveProcessHierarchyMetadata() {
        // save ancestor processes meta.xml files
        for (TempProcess tempProcess : this.processes) {
            if (this.processes.indexOf(tempProcess) == 0) {
                processMetadataTab.preserve();
            }
            saveTempProcessMetadata(tempProcess);
        }
        // save child processes meta.xml files
        for (TempProcess tempProcess : this.childProcesses) {
            saveTempProcessMetadata(tempProcess);
        }
    }

    private void saveTempProcessMetadata(TempProcess tempProcess) {
        try (OutputStream out = ServiceManager.getFileService()
                .write(ServiceManager.getProcessService().getMetadataFileUri(tempProcess.getProcess()))) {
            tempProcess.getWorkpiece().setId(tempProcess.getProcess().getId().toString());
            ServiceManager.getMetsService().save(tempProcess.getWorkpiece(), out);
        } catch (IOException e) {
            Helper.setErrorMessage(e.getLocalizedMessage(), logger, e);
        }
    }

    private boolean createProcessesLocation(List<TempProcess> processes) {
        for (TempProcess tempProcess : processes) {
            if (processes.indexOf(tempProcess) > 0 && Objects.isNull(tempProcess.getMetadataNodes())) {
                // skip creating directories for processes that already exist!
                continue;
            }
            try {
                URI processBaseUri = ServiceManager.getFileService().createProcessLocation(tempProcess.getProcess());
                tempProcess.getProcess().setProcessBaseUri(processBaseUri);
            } catch (IOException e) {
                Helper.setErrorMessage(e.getLocalizedMessage(), logger, e);
                try {
                    ServiceManager.getProcessService().remove(tempProcess.getProcess());
                } catch (DataException ex) {
                    Helper.setErrorMessage(e.getLocalizedMessage(), logger, e);
                }
                return false;
            }
        }
        return true;
    }

    private RulesetManagementInterface openRulesetFile(String fileName) throws IOException, RulesetNotFoundException {
        final long begin = System.nanoTime();
        String metadataLanguage = ServiceManager.getUserService().getCurrentUser().getMetadataLanguage();
        priorityList = Locale.LanguageRange.parse(metadataLanguage.isEmpty() ? "en" : metadataLanguage);
        RulesetManagementInterface ruleset = ServiceManager.getRulesetManagementService().getRulesetManagement();
        try {
            ruleset.load(new File(Paths.get(ConfigCore.getParameter(ParameterCore.DIR_RULESETS), fileName).toString()));
        } catch (FileNotFoundException e) {
            List<String> param = new ArrayList<>();
            param.add(fileName);
            throw new RulesetNotFoundException(Helper.getTranslation("rulesetNotFound", param));
        }

        if (logger.isTraceEnabled()) {
            logger.trace("Reading ruleset took {} ms", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - begin));
        }
        return ruleset;
    }

    /**
     * Initialize the list of created processes.
     */
    public void initializeProcesses() {
        try {
            ProcessGenerator processGenerator = new ProcessGenerator();
            processGenerator.generateProcess(template.getId(), project.getId());
            this.processes = new LinkedList<>(Collections.singletonList(
                    new TempProcess(processGenerator.getGeneratedProcess(), new Workpiece())));
            this.processMetadataTab.initializeProcessDetails(getProcesses().get(0).getWorkpiece().getRootElement());
        } catch (ProcessGenerationException e) {
            logger.error(e.getLocalizedMessage());
        }
    }
}
