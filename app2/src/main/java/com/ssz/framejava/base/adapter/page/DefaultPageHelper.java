package com.ssz.framejava.base.adapter.page;


import com.ssz.framejava.base.adapter.func.IPageOperate;

/**
 * @author : zsp
 * time : 2019 11 2019/11/11 8:56
 */
public abstract class DefaultPageHelper<T> implements IPageHelper{

    private IPageOperate mPageOperate;

    protected boolean isFirstPage;
    protected boolean isLastPage;
    protected boolean hasLastPage;
    protected boolean hasNextPage;
    /**  每页的大小 */
    protected int pageSize;
    /**  总数据条数 */
    protected int totalData;
    /**  当前页数 */
    protected int currentPage;
    /**  总页数 */
    protected int totalPages;
    /*********************************   add   *************************************/

    public synchronized void reset(){
        isFirstPage = false;
        isLastPage = false;
        hasNextPage = false;
        hasLastPage = false;
        pageSize = 0;
        totalData = 0;
        currentPage = 1;
        totalPages = 0;
    }

    public abstract T bindData(T t);

    /**
     * 下一页
     */
    @Override
    public synchronized void nextPage() {
        if (hasNextPage && mPageOperate != null){
            final int nextPageNum = currentPage + 1;
            mPageOperate.nextPage(nextPageNum);
        }
    }

    /**
     * 上一页
     */
    @Override
    public synchronized void lastPage() {
        if (hasLastPage && mPageOperate != null){
            final int lastPageNum = currentPage - 1;
            mPageOperate.lastPage(lastPageNum);
        }
    }

    /**
     * 跳转到第几页
     *
     * @param pageNum
     */
    @Override
    public synchronized void jumpToPage(int pageNum) {
        if (canJumpToPage(pageNum)){
            if (null != mPageOperate){
                mPageOperate.jumpToPage(pageNum);
            }
        }
    }

    public boolean canJumpToPage(int pageNum){
        return (0 < pageNum && pageNum < totalPages);
    }

    public void setPageOperate(IPageOperate pageOperate) {
        this.mPageOperate = pageOperate;
    }
}
