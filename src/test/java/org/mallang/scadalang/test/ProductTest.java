/*
 * Copyright 2019 Foreseeti AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mallang.scadalang.test;

import core.Attacker;
import org.junit.jupiter.api.Test;
import org.mal_lang.scadalang.test.*;

public class ProductTest extends SCADALangTest {
    private static class ProductsModel {
        public final Product product = new Product("testProduct");
        public final Product product2 = new Product("testProduct");
        public final AppServer appServer = new AppServer("testAppServer");

        public ProductsModel() {
            appServer.addProducts(product);
            appServer.addProducts(product2);
            product.addAppservers(appServer);
            product2.addAppservers(appServer);
        }
    }

    @Test
    public void testProductInfection() {
        var model = new ProductsModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.appServer.remoteSystemDiscovery);
        attacker.attack();

        model.product.productInfection.assertCompromisedInstantaneously();
        model.product2.productInfection.assertCompromisedInstantaneously();
    }

    @Test
    public void testProductCorruption() {
        var model = new ProductsModel();

        var attacker = new Attacker();
        attacker.addAttackPoint(model.appServer.remoteSystemDiscovery);
        attacker.attack();

        model.product.productCorruption.assertCompromisedInstantaneously();
        model.product2.productCorruption.assertCompromisedInstantaneously();
    }
}
