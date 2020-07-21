package org.mallang.scadalang.test;

import core.Attacker;
import org.junit.jupiter.api.Test;

public class TestBase extends SCADALangTest {
  private static class BaseModel {
      public BaseModel() {
    }
  }

  @Test
  public void testDatabases() {
    var model = new BaseModel();
    var attacker = new Attacker();
    attacker.attack();

  }
}

