//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.01.22 at 01:42:02 PM EST 
//


package org.ed_fi._0100;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * StaffCohortAssociation record  with key fields: StaffReference (StaffUniqueStateId), CohortReference (CohortIdentifier, StateOrganizationId) and BeginDate. Limited StaffReference and CohortReference to a single instance. Changed types of StaffReference and CohortReference to SLC reference types. 
 * 
 * <p>Java class for SLC-StaffCohortAssociation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SLC-StaffCohortAssociation">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ed-fi.org/0100}ComplexObjectType">
 *       &lt;sequence>
 *         &lt;element name="StaffReference" type="{http://ed-fi.org/0100}SLC-StaffReferenceType"/>
 *         &lt;element name="CohortReference" type="{http://ed-fi.org/0100}SLC-CohortReferenceType"/>
 *         &lt;element name="BeginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="StudentRecordAccess" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SLC-StaffCohortAssociation", propOrder = {
    "staffReference",
    "cohortReference",
    "beginDate",
    "endDate",
    "studentRecordAccess"
})
public class SLCStaffCohortAssociation
    extends ComplexObjectType
{

    @XmlElement(name = "StaffReference", required = true)
    protected SLCStaffReferenceType staffReference;
    @XmlElement(name = "CohortReference", required = true)
    protected SLCCohortReferenceType cohortReference;
    @XmlElement(name = "BeginDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar beginDate;
    @XmlElement(name = "EndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "StudentRecordAccess")
    protected Boolean studentRecordAccess;

    /**
     * Gets the value of the staffReference property.
     * 
     * @return
     *     possible object is
     *     {@link SLCStaffReferenceType }
     *     
     */
    public SLCStaffReferenceType getStaffReference() {
        return staffReference;
    }

    /**
     * Sets the value of the staffReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link SLCStaffReferenceType }
     *     
     */
    public void setStaffReference(SLCStaffReferenceType value) {
        this.staffReference = value;
    }

    /**
     * Gets the value of the cohortReference property.
     * 
     * @return
     *     possible object is
     *     {@link SLCCohortReferenceType }
     *     
     */
    public SLCCohortReferenceType getCohortReference() {
        return cohortReference;
    }

    /**
     * Sets the value of the cohortReference property.
     * 
     * @param value
     *     allowed object is
     *     {@link SLCCohortReferenceType }
     *     
     */
    public void setCohortReference(SLCCohortReferenceType value) {
        this.cohortReference = value;
    }

    /**
     * Gets the value of the beginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBeginDate() {
        return beginDate;
    }

    /**
     * Sets the value of the beginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBeginDate(XMLGregorianCalendar value) {
        this.beginDate = value;
    }

    /**
     * Gets the value of the endDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * Gets the value of the studentRecordAccess property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStudentRecordAccess() {
        return studentRecordAccess;
    }

    /**
     * Sets the value of the studentRecordAccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStudentRecordAccess(Boolean value) {
        this.studentRecordAccess = value;
    }

}
