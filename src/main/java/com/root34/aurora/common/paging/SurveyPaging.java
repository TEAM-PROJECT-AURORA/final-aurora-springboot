package com.root34.aurora.common.paging;

import java.util.List;

/**
	@ClassName : SurveyPaging
	@Date : 2023-04-07
	@Writer : 오승재
	@Description : 설문 페이징 처리용
*/
public class SurveyPaging {

    public static List applyPaging(List list, SelectCriteria selectCriteria) {

        List listWithPaging;

        if(selectCriteria.getTotalCount() >= selectCriteria.getEndRow()) {
            listWithPaging = list.subList(selectCriteria.getStartRow(), selectCriteria.getEndRow());
        } else {
            listWithPaging = list.subList(selectCriteria.getStartRow(), selectCriteria.getTotalCount());
        }

        return listWithPaging;
    }
}
