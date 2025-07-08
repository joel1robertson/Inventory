package org.joro.inventory.ui.component.adminpanel;

import java.util.List;

/**
 *
 * @param <S> Summary type
 * @param <D> Detail type, extends summary type
 */
public interface AdminPanelService<S, D extends S> {

    List<S> fetchSummaries();

    S fetchSummaryFromDetail(D detail);

    D fetchDetailFromSummary(S summary);

    D createDetail();

    D saveDetail(D detail);

    void remove(S summary);
}
