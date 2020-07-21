package com.pojoMAL;

import com.jsonworkers.AttacksDatabaseWorker;
import com.constants.MAL_Syntax;
import com.jsonworkers.CommentsWorker;

import java.util.HashSet;
import java.util.Set;

public class Attack {
    private String name;
    private String logicType;
    //private String userInfo;
    private String developerInfo;
    //private String modelerInfo;
    //private String resultOfAttack;
    //private Set<String> targetFields;
    private Set<String> resultsOfAttack;
    //private String distributionProbability;
    private String assetName;

    public Attack(String name, String logicType, String assetName){
        this.name = name;
        this.logicType = logicType;
        this.resultsOfAttack = new HashSet<>();
        this.assetName = assetName;
    }

    public String getName(){
        return name;
    }

    public Set<String> getResultsOfAttack() {
        return resultsOfAttack;
    }

    public String getLogicType(){
        return  logicType;
    }

    public void addResultOfAttack(String result){
        resultsOfAttack.add(result);
    }
    public void setResultsOfAttack(Set<String> resultsOfAttack) {
        this.resultsOfAttack = resultsOfAttack;
    }

    @Override
    public String toString(){
        String resultOfAttack = "";
        if (resultOfAttack != null){
            for (String r : resultsOfAttack) {
                resultOfAttack = resultOfAttack + r + ",\n\t\t\t";
            }
        }
        StringBuilder result = new StringBuilder();
        /*if (AttacksDatabaseWorker.getExistsInfo(name) != null){
            result.append("\t" + AttacksDatabaseWorker.getExistsInfo(name).get(0) + "\n" +
                    "\t\t" + AttacksDatabaseWorker.getExistsInfo(name).get(1) + "\n" +
                    "\t\t" + MAL_Syntax.MAL_RESULT + name + "\n");
        }*/
        result.append("\t" + logicType + " " + name);
        /*if (AttacksDatabaseWorker.lookupRisks(name) != null) {
            result.append(" {" + AttacksDatabaseWorker.lookupRisks(name) + "} ");
        }*/
        if (AttacksDatabaseWorker.getDistribution(name) != null) {
            result.append(" " + AttacksDatabaseWorker.getDistribution(name) + "\n");
        }
        if (CommentsWorker.lookupDeveloperCommentsPerAttack(assetName, name) != null
                && CommentsWorker.lookupDeveloperCommentsPerAttack(assetName, name).length() != 0) {
            //result.append("\t\t" + MAL_Syntax.USER_INFO + CommentsWorker.lookupUserCommentsPerAttack(assetName, name) + "\n");
            //result.append("\t\t" + MAL_Syntax.DEVELOPER_INFO + "\"" + CommentsWorker.lookupDeveloperCommentsPerAttack(assetName, name) + "\"" + "\n");
            //result.append("\t\t" + MAL_Syntax.MODELER_INFO + CommentsWorker.lookupModelerCommentsPerAttack(assetName, name)+ "\n");
        }
        if (resultOfAttack.length() !=0) {
            result.append("\t\t" + MAL_Syntax.MAL_RESULT + " " +
                    resultOfAttack.substring(0, resultOfAttack.length() - 5) + "\n");
        }
        return result.toString();
    }
}
