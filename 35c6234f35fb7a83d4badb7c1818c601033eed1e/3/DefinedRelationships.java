package uk.co.compendiumdev.thingifier.domain.definitions;

import uk.co.compendiumdev.thingifier.domain.definitions.relationship.RelationshipVector;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefinedRelationships {

    private Map<String, List<RelationshipVector>> relationships;

    public DefinedRelationships(){
        this.relationships = new ConcurrentHashMap<>();
    }

    public boolean hasRelationship(final String relationshipName) {
        return relationships.containsKey(relationshipName.toLowerCase());
    }

    public RelationshipVector getRelationship(final String relationshipName,
                                              final ThingDefinition toEntityDefinition) {
        List<RelationshipVector> relationshipsWithThisName =
                this.relationships.get(relationshipName.toLowerCase());
        if (relationshipsWithThisName == null) {
            // there is no relationship with this name
            return null;
        }

        for (RelationshipVector relationship : relationshipsWithThisName) {
            if (relationship.getTo().definition() == toEntityDefinition) {
                return relationship;
            }
        }

        // there is no relationship with this name between the things we want
        return null;
    }

    public void addRelationship(final RelationshipVector relationship) {
        List<RelationshipVector> relationshipsWithThisName = relationships.get(relationship.getName());
        if (relationshipsWithThisName == null) {
            // there is no relationship with this name
            relationshipsWithThisName = new ArrayList<>();
            relationships.put(relationship.getName(), relationshipsWithThisName);
        }

        relationshipsWithThisName.add(relationship);
    }

    public List<RelationshipVector> getRelationships(final String relationshipName) {
        List<RelationshipVector> myrelationships = relationships.get(relationshipName.toLowerCase());
        return new ArrayList<>(myrelationships);
    }

    public Set<RelationshipVector> getRelationships() {

        Set<RelationshipVector> myRelationships = new HashSet<>();
        for (List<RelationshipVector> list : relationships.values()) {
            for (RelationshipVector rel : list) {
                myRelationships.add(rel);
            }

        }
        return myRelationships;
    }
}
