package com.suggestedassets;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;
import com.pojoMAL.AssetSCADA;
import com.pojoMAL.Field;

import java.util.List;

public class Diod extends AssetSCADA {

    public Diod (String name, RelationshipCodes type, List<Field> fields) {
        super(name, name, type, AttacksDatabaseWorker.getAssetsAttackTags("Diod"), fields);
        this.userInfo = "A data diode is hardware device and alternative for firewalls and USG. An optical fiber with a sender on one side and a receiver on the other ensures that data can only be transferred in a forward direction, and never in reverse. ";
        this.developerInfo = "This is a security solution that controls the flow of traffic in a network";
        this.modelerInfo = "The data diode needs to be placed between teo zones. It is expensive solutions used in military and recently in a energy sector";
    }
}
