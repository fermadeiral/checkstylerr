package org.openmrs.module.sync2.api.model.audit;


import java.lang.reflect.Type;
import java.util.Date;
import java.util.Objects;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.openmrs.BaseOpenmrsData;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name = "sync2.syncauditmessage")
@Table(name = "sync_audit_message")
public class AuditMessage extends BaseOpenmrsData {

    @Id
    @GeneratedValue
    @Column(name = "sync_audit_message_id")
    private Integer id;

    @Basic
    @Column(name = "success")
    private Boolean success;

    @Basic
    @Column(name = "timestamp")
    private Date timestamp;

    @Basic
    @Column(name = "resource_name")
    private String resourceName;

    @Basic
    @Column(name = "resource_url")
    private String resourceUrl;

    @Basic
    @Column(name = "error")
    private String error;

    @Basic
    @Column(name = "action")
    private String action;


    public AuditMessage() {
    }

    public AuditMessage(Integer id, Boolean success, Date timestamp, String resourceName, String resourceUrl,
                        String error, String action) {
        this.id = id;
        this.success = success;
        this.timestamp = timestamp;
        this.resourceName = resourceName;
        this.resourceUrl = resourceUrl;
        this.error = error;
        this.action = action;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getUuid() {
        return super.getUuid();
    }

    @Override
    public void setUuid(String uuid) {
        super.setUuid(uuid);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuditMessage auditMessage = (AuditMessage) o;
        return Objects.equals(this.success, auditMessage.success)
                && Objects.equals(this.timestamp, auditMessage.timestamp)
                && Objects.equals(this.resourceName, auditMessage.resourceName)
                && Objects.equals(this.resourceUrl, auditMessage.resourceUrl)
                && Objects.equals(this.error, auditMessage.error)
                && Objects.equals(this.action, auditMessage.action);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, timestamp, resourceName, resourceUrl, error, action);
    }

    public static class AuditMessageSerializer implements JsonSerializer<AuditMessage> {

        @Override
        public JsonElement serialize(AuditMessage src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject object = new JsonObject();

            object.addProperty("id", src.id);
            object.addProperty("uuid", src.getUuid());
            object.addProperty("success", src.success);
            object.addProperty("timestamp", src.timestamp.toString());
            object.addProperty("resourceName", src.resourceName);
            object.addProperty("resourceUrl", src.resourceUrl);
            object.addProperty("error", src.error);
            object.addProperty("action", src.action);

            return object;
        }
    }

}