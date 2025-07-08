package org.joro.inventory.ui.view.admin.linkcategory;

import org.joro.inventory.ui.model.data.LinkCategory;
import org.joro.inventory.service.LinkCategoryService;
import org.joro.inventory.ui.component.adminpanel.AdminPanelService;

import java.util.List;

public class LinkCategoryAdminPanelService implements AdminPanelService<LinkCategory, LinkCategory> {

    private final LinkCategoryService linkCategoryService;

    public LinkCategoryAdminPanelService(LinkCategoryService linkCategoryService) {
        this.linkCategoryService = linkCategoryService;
    }

    @Override
    public List<LinkCategory> fetchSummaries() {
        return linkCategoryService.fetchAll();
    }

    @Override
    public LinkCategory fetchSummaryFromDetail(LinkCategory linkCategory) {
        return linkCategory;
    }

    @Override
    public LinkCategory fetchDetailFromSummary(LinkCategory linkCategory) {
        return linkCategory;
    }

    @Override
    public LinkCategory createDetail() {
        return new LinkCategory();
    }

    @Override
    public LinkCategory saveDetail(LinkCategory linkCategory) {
        return linkCategoryService.save(linkCategory);
    }

    @Override
    public void remove(LinkCategory linkCategory) {
        linkCategoryService.remove(linkCategory);
    }
}
