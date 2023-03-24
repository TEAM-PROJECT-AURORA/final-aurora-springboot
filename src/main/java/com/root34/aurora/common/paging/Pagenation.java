package com.root34.aurora.common.paging;

public class Pagenation {

    public static SelectCriteria getSelectCriteria(int pageNo, int totalCount, int limit, int buttonAmount) {

        int maxPage;			//전체 페이지에서 가장 마지막 페이지
        int startPage;			//한번에 표시될 페이지 버튼의 시작할 페이지
        int endPage;			//한번에 표시될 페이지 버튼의 끝나는 페이지
        int startRow;
        int endRow;

        maxPage = (int) Math.ceil((double) totalCount / limit);

        startPage = (int) (Math.ceil((double) pageNo / buttonAmount) - 1) * buttonAmount + 1;

        endPage = startPage + buttonAmount - 1;

        if(maxPage < endPage){
            endPage = maxPage;
        }

        if(maxPage == 0 && endPage == 0) {
            maxPage = startPage;
            endPage = startPage;
        }

        //startRow = (pageNo - 1) * limit + 1; //오라클에선 이렇게 해서 첫번째 데이터를 가져온다
        startRow = (pageNo - 1) * limit; // mysql에선 이렇게 해서 첫번째 데이터를 가져온다
        endRow = startRow + limit - 1;

        System.out.println("startRow : " + startRow);
        System.out.println("endRow : " + endRow);

        SelectCriteria selectCriteria = new SelectCriteria(pageNo, totalCount, limit, buttonAmount ,maxPage, startPage, endPage, startRow, endRow);

        return selectCriteria;
    }
}
