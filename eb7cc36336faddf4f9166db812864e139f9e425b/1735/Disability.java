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
 * This type represents an impairment of body structure or function, a limitation in activities, or a restriction in participation, as ordered by severity of impairment.
 * 
 * <p>Java class for Disability complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Disability">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Disability" type="{http://ed-fi.org/0100}DisabilityType"/>
 *         &lt;element name="DisabilityDiagnosis" type="{http://ed-fi.org/0100}DisabilityDiagnosis" minOccurs="0"/>
 *         &lt;element name="OrderOfDisability" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Disability", propOrder = {
    "disability",
    "disabilityDiagnosis",
    "orderOfDisability"
})
public class Disability {

    @XmlElement(name = "Disability", required = true)
    protected DisabilityType disability;
    @XmlElement(name = "DisabilityDiagnosis")
    protected String disabilityDiagnosis;
    @XmlElement(name = "OrderOfDisability")
    protected Integer orderOfDisability;

    /**
     * Gets the value of the disability property.
     * 
     * @return
     *     possible object is
     *     {@link DisabilityType }
     *     
     */
    public DisabilityType getDisability() {
        return disability;
    }

    /**
     * Sets the value of the disability property.
     * 
     * @param value
     *     allowed object is
     *     {@link DisabilityType }
     *     
     */
    public void setDisability(DisabilityType value) {
        this.disability = value;
    }

    /**
     * Gets the value of the disabilityDiagnosis property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisabilityDiagnosis() {
        return disabilityDiagnosis;
    }

    /**
     * Sets the value of the disabilityDiagnosis property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisabilityDiagnosis(String value) {
        this.disabilityDiagnosis = value;
    }

    /**
     * Gets the value of the orderOfDisability property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getOrderOfDisability() {
        return orderOfDisability;
    }

    /**
     * Sets the value of the orderOfDisability property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setOrderOfDisability(Integer value) {
        this.orderOfDisability = value;
    }

}
