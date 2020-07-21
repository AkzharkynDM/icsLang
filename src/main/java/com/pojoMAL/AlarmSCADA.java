package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.RelationshipCodes;

import java.util.List;

public class AlarmSCADA extends AssetSCADA {
    public AlarmSCADA(String name, RelationshipCodes type, List<Field> fields){
        super(name, "Alarm", type, AttacksDatabaseWorker.getAssetsAttackTags("alarm"), fields);
        this.userInfo = "Examples of alarm indicators include a siren, a pop-up box on a screen, or a coloured or " +
                "flashing area on a screen (that might act in a similar way to the 'fuel tank empty' light in a car); " +
                "in each case, the role of the alarm indicator is to draw the operator's attention to the part " +
                "of the system 'in alarm' so that appropriate action can be taken.";
        this.modelerInfo = "An important part of most SCADA implementations is alarm handling." +
                "Once an alarm event has been detected, one or more actions are taken (such as the activation of one " +
                "or more alarm indicators, and perhaps the generation of email or text messages so that management " +
                "or remote SCADA operators are informed).";
    }
}
