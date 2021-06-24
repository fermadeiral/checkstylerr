package uk.co.compendiumdev.thingifier.domain.definitions.relationship;

import uk.co.compendiumdev.thingifier.Thing;
import uk.co.compendiumdev.thingifier.domain.definitions.Cardinality;

import static uk.co.compendiumdev.thingifier.domain.definitions.relationship.Optionality.*;

/**
 * A relationshipVector is the definition of the variant of relationship from one thing to another
 * e.g. a specific a -> b relationship might have a different name from the main relationships
 *
 * task <- estimates /estimate of-> estimate
 *
 * task to estimate would be called 'estimates' and would be 1(o):M i.e. 1 task can have 0 to many estimates
 * estimate to task would be called 'estimate-of' and would be 1:1 an estimate must have 1 task
 *
 * todo: in theory we don't need 'relationship' since we could just create two vectors
 *
 */
public class RelationshipVector {

    private final String name;
    private final Cardinality cardinality;
    private Optionality optionality;
    private Thing from;
    private Thing to;
    private RelationshipDefinition parentRelationship;

    public RelationshipVector(Thing from, String relationShipName, Thing to, Cardinality cardinality) {
        this.from = from;
        this.name = relationShipName;
        this.to = to;
        this.cardinality = cardinality;
        this.optionality = OPTIONAL_RELATIONSHIP;
    }

    public String getName() {
        return name;
    }

    public Cardinality getCardinality() {
        return cardinality;
    }

    public Thing getTo() {
        return to;
    }

    public Thing getFrom() {
        return from;
    }

    public RelationshipDefinition getRelationshipDefinition() {
        return parentRelationship;
    }

    public void setOptionality(final Optionality aGivenOptionality) {
        this.optionality = aGivenOptionality;
    }

    public Optionality getOptionality() {
        return optionality;
    }

    public void forRelationship(final RelationshipDefinition relationshipDefinition) {
        this.parentRelationship = relationshipDefinition;
    }
}
