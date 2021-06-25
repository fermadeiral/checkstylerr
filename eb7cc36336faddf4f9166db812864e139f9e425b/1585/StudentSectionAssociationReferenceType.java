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
import javax.xml.bind.annotation.XmlType;


/**
 * Provides alternative references for the Student-section association. Use XML IDREF to reference a program record that is included in the interchange
 * 
 * <p>Java class for StudentSectionAssociationReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudentSectionAssociationReferenceType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ed-fi.org/0100}ReferenceType">
 *       &lt;sequence>
 *         &lt;element name="StudentSectionAssociationIdentity" type="{http://ed-fi.org/0100}StudentSectionAssociationIdentityType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudentSectionAssociationReferenceType", propOrder = {
    "studentSectionAssociationIdentity"
})
public class StudentSectionAssociationReferenceType
    extends ReferenceType
{

    @XmlElement(name = "StudentSectionAssociationIdentity")
    protected StudentSectionAssociationIdentityType studentSectionAssociationIdentity;

    /**
     * Gets the value of the studentSectionAssociationIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link StudentSectionAssociationIdentityType }
     *     
     */
    public StudentSectionAssociationIdentityType getStudentSectionAssociationIdentity() {
        return studentSectionAssociationIdentity;
    }

    /**
     * Sets the value of the studentSectionAssociationIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudentSectionAssociationIdentityType }
     *     
     */
    public void setStudentSectionAssociationIdentity(StudentSectionAssociationIdentityType value) {
        this.studentSectionAssociationIdentity = value;
    }

}
