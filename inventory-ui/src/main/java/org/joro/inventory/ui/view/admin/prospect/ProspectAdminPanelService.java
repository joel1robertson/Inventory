package org.joro.inventory.ui.view.admin.prospect;

import org.joro.inventory.ui.model.data.Prospect;
import org.joro.inventory.service.ProspectService;
import org.joro.inventory.ui.component.adminpanel.AdminPanelService;

import java.util.List;

public class ProspectAdminPanelService implements AdminPanelService<Prospect, Prospect> {

    private final ProspectService prospectService;

    public ProspectAdminPanelService(ProspectService prospectService) {
        this.prospectService = prospectService;
    }

    @Override
    public List<Prospect> fetchSummaries() {
        return prospectService.fetchAll();
    }

    @Override
    public Prospect fetchSummaryFromDetail(Prospect prospect) {
        return prospect;
    }

    @Override
    public Prospect fetchDetailFromSummary(Prospect prospect) {
        return prospect;
    }

    @Override
    public Prospect createDetail() {
        return new Prospect();
    }

    @Override
    public Prospect saveDetail(Prospect prospect) {
        return prospectService.save(prospect);
    }

    @Override
    public void remove(Prospect prospect) {
        prospectService.remove(prospect);
    }
}
