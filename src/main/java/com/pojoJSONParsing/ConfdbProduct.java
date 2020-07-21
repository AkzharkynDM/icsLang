package com.pojoJSONParsing;
public class ConfdbProduct extends ParsedConfDBElement {
   // private String build_version;
    private String dependent_of_the_products;
    private String install_topdir;
    private String installation_priority;
    private String installed;
   // private String kit_version;
    private String option_name;
    private String populated;
    private String product_file_system;
    private String product_file_system_owner;
    private String product_file_system_specific;
    private String product_kit_name;
    private String server_type;

    public ConfdbProduct(String category_name,
                        // String build_version,
                         String dependent_of_the_products,
                         String install_topdir,
                         String installation_priority,
                         String installed,
                      //   String kit_version,
                         String option_name,
                         String populated,
                         String product_file_system,
                         String product_file_system_owner,
                         String product_file_system_specific,
                         String product_kit_name,
                         String server_type) {
        super(category_name);
     //   this.build_version = build_version;
        this.dependent_of_the_products = dependent_of_the_products;
        this.install_topdir = install_topdir;
        this.installation_priority = installation_priority;
        this.installed = installed;
       // this.kit_version = kit_version;
        this.option_name = option_name;
        this.populated = populated;
        this.product_file_system = product_file_system;
        this.product_file_system_owner = product_file_system_owner;
        this.product_file_system_specific = product_file_system_specific;
        this.product_kit_name = product_kit_name;
        this.server_type = server_type;
    }
}
