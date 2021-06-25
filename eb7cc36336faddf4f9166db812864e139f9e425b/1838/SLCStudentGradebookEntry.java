//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.22 at 01:42:02 PM EST 
//


package org.ed_fi._0100;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * StudentGradebookEntry record with key fields: StudentSectionAssociationReference (StudentUniqueStateId, StateOrganizationId, UniqueSectionCode, BeginDate) and GradebookEntryReference (GradebookEntryType, DateAssigned, StateOrganizationId, UniqueSectionCode). Changed types of StudentSectionAssociationReference and GradebookEntryReference to SLC reference types.
 * 
 * <p>Java class for SLC-StudentGradebookEntry complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SLC-StudentGradebookEntry">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ed-fi.org/0100}ComplexObjectType">
 *       &lt;sequence>
 *         &lt;element name="DateFulfilled" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="LetterGradeEarned" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://ed-fi.org/0100}GradeEarned">
 *               &lt;maxLength value="20"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="NumericGradeEarned" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="CompetencyLevel" type="{http://ed-fi.org/0100}CompetencyLevelDescriptorType" minOccurs="0"/>
 *         &lt;element name="DiagnosticStatement" type="{http://ed-fi.org/0100}text" minOccurs="0"/>
 *         &lt;element name="StudentSectionAssociationReference" type="{http://ed-fi.org/0100}SLC-StudentSectionAssociationReferenceType"/>
 *         &lt;element name="GradebookEntryReference" type="{http://ed-fi.org/0100}SLC-GradebookEntryReferenceType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SLC-StudentGradebookEntry", propOrder = {
    "dateFulfilled",
    "letterGradeEarned",
    "numericGradeEarned",
    "competencyLevel",
    "diagnosticStatement",
    "studentSectionAssociationReference",
    "gradebookEntryReference"
})
public class SLCStudentGradebookEntry
    extends ComplexObjectType
{

    @XmlElement(name = "DateFulfilled")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateFulfilled;
    @XmlElement(name = "LetterGradeEarned")
    protected String letterGradeEarned;
    @XmlElement(name = "NumericGradeEarned")
    protected BigInteger numericGradeEarned;
    @XmlElement(name = "CompetencyLevel")
    protected CompetencyLevelDescriptorType competencyLevel;
    @XmlElement(name = "DiagnosticStatement")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String diagnosticStatement;
    @XmlElement(name = "StudentSectionAssociationReference", required = true)
    protected SLCStudentSectionAssociationReferenceType studentSectionAssociationReference;
    @XmlElement(name = "GradebookEntryReference", required = true)
    protected SLCGradebookEntryReferenceType gradebookEntryReference;

    /**
     * Gets the value of the dateFulfilled property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateFulfilled() {
        return dateFulfilled;
    }

    /**
     * Sets the value of the dateFulfilled property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateFulfilled(XMLGregorianCalendar value) {
        this.dateFulfilled = value;
    }

    /**
     * Gets the value of the letterGradeEarned property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLetterGradeEarned() {
        return letterGradeEarned;
    }

    /**
     * Sets the value of the letterGradeEarned property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLetterGradeEarned(String value) {
        this.letterGradeEarned = value;
    }

    /**
     * Gets the value of the numericGradeEarned property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumericGradeEarned() {
        return numericGradeEarned;
    }

    /**
     * Sets the value of the numericGradeEarned property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumericGradeEarned(BigInteger value) {
        this.numericGradeEarned = value;
    }

    /**
     * Gets the value of the competencyLevel property.
     * 
     * @return
     *     possible object is
     *     {@link CompetencyLevelDescriptorType }
     *     
     */
    public CompetencyLevelDescriptorType getCompetencyLevel() {
        return competencyLevel;
    }

    /**
     * Sets the value of the competencyLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link CompetencyLevelDescriptorType }
     *     
     */
    public void setCompetencyLevel(CompetencyLevelDescriptorType value) {
        this.competencyLevel = value;
    }

    /**
     * Gets the value of the diagnosticStatement property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiagnosticStatement() {
        return diagnosticStatement;
    }

    /**
     * Sets the value of the diagnosticStatement property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiagnosticStatement(String value) {
        this.diagnosticStatement = value;
    }

    /**
     * Gets the value of the studentSectionAssociationReference property.
     * 
     * @return
     *     possible object is
     *     {@link SLCStudentSectionAssociationReferenceType }
     *     
     */
    public SLCStudentSectionAssociationReferenceType getStudentSectionAssociationReference() {
        return studentSectionAssociationReference;
    }

    /**
     * Sets the value of the studentSectionAssociationReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link SLCStudentSectionAssociationReferenceType }
     *     
     */
    public void setStudentSectionAssociationReference(SLCStudentSectionAssociationReferenceType value) {
        this.studentSectionAssociationReference = value;
    }

    /**
     * Gets the value of the gradebookEntryReference property.
     * 
     * @return
     *     possible object is
     *     {@link SLCGradebookEntryReferenceType }
     *     
     */
    public SLCGradebookEntryReferenceType getGradebookEntryReference() {
        return gradebookEntryReference;
    }

    /**
     * Sets the value of the gradebookEntryReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link SLCGradebookEntryReferenceType }
     *     
     */
    public void setGradebookEntryReference(SLCGradebookEntryReferenceType value) {
        this.gradebookEntryReference = value;
    }

}
