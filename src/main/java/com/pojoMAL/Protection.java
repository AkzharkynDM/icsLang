package com.pojoMAL;

import com.constants.MAL_Syntax;

import java.util.List;

public class Protection {
    private String nameProtection;
    //private String leadsToAttack;
    private List<String> leadsToAttacks;

    /*public Protection(String nameProtection, String leadsToAttack){
        this.nameProtection = nameProtection;
        this.leadsToAttack = leadsToAttack;
    }*/

    public Protection(String nameProtection, List<String> leadsToAttacks){
        this.nameProtection = nameProtection;
        this.leadsToAttacks = leadsToAttacks;
    }

    /*@Override
    public String toString(){
        return "\t" + MAL_Syntax.MAL_PREVENT + " " + nameProtection + "\n"
                + "\t\t" + MAL_Syntax.MAL_RESULT + leadsToAttack + "\n";
    }*/

    @Override
    public String toString(){
        String resultOfAttack = "";
        if (resultOfAttack != null){
            for (String r : leadsToAttacks) {
                resultOfAttack = resultOfAttack + r + ",\n\t\t\t";
            }
        }
        StringBuilder result = new StringBuilder();

        result.append("\t" + MAL_Syntax.MAL_PREVENT + nameProtection + "\n");
        if (resultOfAttack.length() !=0) {
            result.append("\t\t" + MAL_Syntax.MAL_RESULT + " " +
                    resultOfAttack.substring(0, resultOfAttack.length() - 5) + "\n");
        }
        return result.toString();
    }
}
