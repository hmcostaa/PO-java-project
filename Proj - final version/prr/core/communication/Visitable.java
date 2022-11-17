package prr.core.communication;

import prr.core.tariff.TariffPlan;

public interface Visitable {

    public double accept(TariffPlan tariff);
}
