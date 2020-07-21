package org.mallang.scadalang.test;

import core.Attacker;
import org.junit.jupiter.api.Test;
import org.mal_lang.scadalang.test.*;

public class TestNetworks extends SCADALangTest {
  private static class CommunicationsModel {
    public final ProductionZone prodZone = new ProductionZone("testProd");
    public final DemilitarizedZone dmZone = new DemilitarizedZone("testDMZ");
   public final Firewall firewall = new Firewall("testFirewall");
    //public final Router router = new Router("testRouter");
    public final Diod diod = new Diod("testDiod");
    public final UnidirectionalGateway unidirectionalGateway = new UnidirectionalGateway("testUDG");

      public CommunicationsModel() {
        prodZone.addDiod(diod);
        prodZone.addFirewall(firewall);
        prodZone.addUnidirectionalgateway(unidirectionalGateway);
        dmZone.addDiod(diod);
        dmZone.addFirewall(firewall);
        dmZone.addUnidirectionalgateway(unidirectionalGateway);
        diod.addDemilitarizedzone(dmZone);
        diod.addProductionzone(prodZone);
        firewall.addDemilitarizedzone(dmZone);
        firewall.addProductionzone(prodZone);
        //firewall.addRouter(router);
        unidirectionalGateway.addDemilitarizedzone(dmZone);
        unidirectionalGateway.addProductionzone(prodZone);
    }
  }

  @Test
  public void testFirewallBypass() {
    var model = new CommunicationsModel();

    var attacker = new Attacker();
    attacker.addAttackPoint(model.prodZone.bypassFirewall);
    attacker.addAttackPoint(model.dmZone.bypassFirewall);
    attacker.attack();

    model.dmZone.gainInitialAccess.assertUncompromised();
  }
}
