/* 
 * Copyright 2015 Torridity.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.tor.tribes.util.report;

import de.tor.tribes.control.GenericManager;
import de.tor.tribes.control.ManageableType;
import de.tor.tribes.types.FightReport;
import de.tor.tribes.types.ext.Village;
import de.tor.tribes.util.SystrayHelper;
import de.tor.tribes.util.farm.FarmManager;
import de.tor.tribes.util.village.KnownVillageManager;
import de.tor.tribes.util.xml.JDomUtils;
import java.net.URLDecoder;
import java.util.*;
import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jdom2.Element;

/**
 *
 * @author Torridity
 */
public class ReportManager extends GenericManager<FightReport> {

  private static Logger logger = LogManager.getLogger("ReportManager");
  private static ReportManager SINGLETON = null;
  public final static String FARM_SET = "Farmberichte";
  private List<RuleEntry> rules = new LinkedList<>();
  private final FarmReportFilter farmFilter = new FarmReportFilter();

  public static synchronized ReportManager getSingleton() {
    if (SINGLETON == null) {
      SINGLETON = new ReportManager();
    }
    return SINGLETON;
  }

  ReportManager() {
    super(true);
    addGroup(FARM_SET);
  }

  @Override
  public void initialize() {
    super.initialize();
    addGroup(FARM_SET);
  }

  @Override
  public String[] getGroups() {
    String[] groups = super.getGroups();
    Arrays.sort(groups, new Comparator<String>() {

      @Override
      public int compare(String o1, String o2) {
        if (o1.equals(DEFAULT_GROUP) || o1.equals(FARM_SET)) {
          return -1;
        } else if (o2.equals(DEFAULT_GROUP) || o2.equals(FARM_SET)) {
          return 1;
        } else {
          return String.CASE_INSENSITIVE_ORDER.compare(o1, o2);
        }
      }
    });
    return groups;
  }

  public RuleEntry[] getRuleEntries() {
    return rules.toArray(new RuleEntry[rules.size()]);
  }

  public void addRule(ReportRuleInterface pFilter, String pToSet) {
    rules.add(new RuleEntry(pFilter, pToSet));
  }

  public void removeRule(ReportRuleInterface pFilter) {
    for (RuleEntry rule : rules.toArray(new RuleEntry[rules.size()])) {
      if (rule.getRule().equals(pFilter)) {
        rules.remove(rule);
        break;
      }
    }
  }

  @Override
  public void addManagedElement(final FightReport pElement) {
    boolean filtered = false;
    
    //update information of the VIllages
    KnownVillageManager.getSingleton().updateInformation(pElement);
    
    if (farmFilter.isValid(pElement)) {
      logger.debug("Farm filter was activated for village " + pElement.getTargetVillage());
      FarmManager.getSingleton().updateFarmInfoFromReport(pElement);
      addManagedElement(FARM_SET, pElement, false);
    } else {
      for (RuleEntry entry : getRuleEntries()) {
        if (entry.getRule().isValid(pElement)) {
          super.addManagedElement(entry.getTargetSet(), pElement);
          filtered = true;
          break;
        }
      }

      if (!filtered) {
        super.addManagedElement(pElement);
      }
      SystrayHelper.showInfoMessage("Bericht erfolgreich eingelesen");
    }
  }

  @Override
  public void addManagedElement(String pGroup, final FightReport pElement) {
    addManagedElement(pGroup, pElement, false);
  }

  public void addManagedElement(String pGroup, final FightReport pElement, boolean pFiltered) {
    boolean filtered = false;
    if (pFiltered) {
      if (farmFilter.isValid(pElement)) {
        logger.debug("Farm filter was activated for village " + pElement.getTargetVillage());
        FarmManager.getSingleton().updateFarmInfoFromReport(pElement);
        addManagedElement(FARM_SET, pElement, false);
      } else {
        for (RuleEntry entry : getRuleEntries()) {
          if (entry.getRule().isValid(pElement)) {
            addManagedElement(entry.getTargetSet(), pElement);
            filtered = true;
            break;
          }
        }
      }
      if (!filtered) {
        super.addManagedElement(pGroup, pElement);
      }
      SystrayHelper.showInfoMessage("Bericht erfolgreich eingelesen");
    } else {//add element without filtering
      super.addManagedElement(pGroup, pElement);
    }
  }

  public int filterNow(String pGroup) {
    invalidate();
    try {
      HashMap<FightReport, String> newGroups = new HashMap<>();
      for (ManageableType t : getAllElements(pGroup)) {
        FightReport report = (FightReport) t;
        for (RuleEntry entry : getRuleEntries()) {
          if (entry.getRule().isValid(report)) {
            if (!entry.getTargetSet().equals(pGroup)) {
              //only move report, if the filter points to a new group...
              //...otherwise, report stays in this group as the current filter is the first fits
              newGroups.put(report, entry.getTargetSet());
            }
            break;
          }
        }
      }

      Set<Entry<FightReport, String>> entries = newGroups.entrySet();
      for (Entry<FightReport, String> entry : entries) {
        //remove report from this group
        removeElement(pGroup, entry.getKey());
        //add report to new group and continue filtering
        addManagedElement(entry.getValue(), entry.getKey(), true);
      }
      return entries.size();
    } finally {
      revalidate(true);
    }
  }

  @Override
    public int importData(Element pElm, String pExtension) {
    if (pElm == null) {
        logger.error("Element argument is 'null'");
        return -1;
    }
    int result = 0;
    invalidate();

    logger.debug("Loading reports");
    try {
      for (Element e : (List<Element>) JDomUtils.getNodes(pElm, "reportSets/reportSet")) {
        String setKey = e.getAttributeValue("name");
        setKey = URLDecoder.decode(setKey, "UTF-8");
        if (pExtension != null) {
          setKey += "_" + pExtension;
        }
        addGroup(setKey);
        if (logger.isDebugEnabled()) {
          logger.debug("Loading report set ''{}", setKey);
        }

        for (Element e1 : (List<Element>) JDomUtils.getNodes(e, "reports/report")) {
          FightReport r = new FightReport();
          r.loadFromXml(e1);
          addManagedElement(setKey, r);
          result++;
        }
      }
      logger.debug("Reports successfully loaded");
      
      for (Element e : (List<Element>) JDomUtils.getNodes(pElm, "rules/rule")) {
        RuleEntry r = new RuleEntry(e);
        rules.add(r);
      }
      logger.debug("Report Rules successfully loaded");
    } catch (Exception e) {
      result = result * (-1) - 1;
      logger.error("Failed to load reports", e);
    } finally {
      revalidate(true);
    }
    return result;
  }

  @Override
  public Element getExportData(final List<String> pGroupsToExport) {
    Element reportSets = new Element("reportSets");
    if (pGroupsToExport == null || pGroupsToExport.isEmpty()) {
        return reportSets;
    }
    logger.debug("Generating report data");
    
    for (String set : pGroupsToExport) {
      Element reportSet = new Element("reportSet");
      reportSet.setAttribute("name", set);
      Element reports = new Element("reports");
      for (ManageableType t : getAllElements(set)) {
        reports.addContent(t.toXml("report"));
      }
      reportSet.addContent(reports);
      reportSets.addContent(reportSet);
    }
    
    Element rulesE = new Element("rules");
    for(RuleEntry r: rules) {
      rulesE.addContent(r.getRule().toXml());
    }
    
    logger.debug("Data generated successfully");
    return reportSets;
  }

  public boolean createReportSet(String pName) {
    return addGroup(pName);
  }

  /**
   * Return the most current report for source pVillage
   *
   * @param pVillage
   * @return
   */
  public FightReport findLastReportForSource(Village pVillage) {
    FightReport current = null;
    for (ManageableType element : getAllElementsFromAllGroups()) {
      FightReport report = (FightReport) element;
      if (report.getSourceVillage() != null && report.getSourceVillage().equals(pVillage)) {
        if (current == null || report.getTimestamp() > current.getTimestamp()) {
          current = report;
        }
      }
    }
    return current;
  }

  /**
   * Return the most current report for target pVillage
   *
   * @param pVillage
   * @return
   */
  public FightReport findLastReportForTarget(Village pVillage) {
    FightReport current = null;
    for (ManageableType element : getAllElementsFromAllGroups()) {
      FightReport report = (FightReport) element;
      if (report.getTargetVillage() != null && report.getTargetVillage().equals(pVillage)) {
        if (current == null || report.getTimestamp() > current.getTimestamp()) {
          current = report;
        }
      }
    }
    return current;
  }

  /**
   * Return all reports for target pVillage
   *
   * @param pVillage
   * @return
   */
  public List<FightReport> findAllReportsForTarget(Village pVillage) {
    List<FightReport> all = new LinkedList<>();
    for (ManageableType element : getAllElementsFromAllGroups()) {
      FightReport report = (FightReport) element;
      if (report.getTargetVillage() != null && report.getTargetVillage().equals(pVillage)) {
        all.add(report);
      }
    }
    return all;
  }

  /**
   * Return the most current report for pVillage
   *
   * @param pVillage
   * @return
   */
  public FightReport findLastReportForVillage(Village pVillage) {
    FightReport current = null;
    for (ManageableType element : getAllElementsFromAllGroups()) {
      FightReport report = (FightReport) element;
      if ((report.getTargetVillage() != null && report.getTargetVillage().equals(pVillage))
              || (report.getSourceVillage() != null && report.getSourceVillage().equals(pVillage))) {
        if (current == null || report.getTimestamp() > current.getTimestamp()) {
          current = report;
        }
      }
    }
    return current;
  }

  public static class RuleEntry {

    private ReportRuleInterface rule = null;
    private String targetSet = null;

    public RuleEntry(ReportRuleInterface pRule, String pTargetSet) {
      rule = pRule;
      targetSet = pTargetSet;
    }

    private RuleEntry(Element e) {
        //TODO create function
    }

    public void setRule(ReportRuleInterface rule) {
      this.rule = rule;
    }

    public ReportRuleInterface getRule() {
      return rule;
    }

    public void setTargetSet(String targetSet) {
      this.targetSet = targetSet;
    }

    public String getTargetSet() {
      return targetSet;
    }

    @Override
    public String toString() {
      return rule.getStringRepresentation() + " -> " + targetSet;
    }
  }
}
