package edu.xnxy.caojx.filemanager.mybatis.mapper.pagination;

/**
 * Description: 分页实体
 *
 * @author caojx
 *         Created by caojx on 2017年04月23 上午9:35:35
 */
public class PageParameter {

    /**
     * 页面显示数目
     */
    private Integer pageSize;

    /**
     * 当前页
     */
    private Integer currentPage;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 总条数
     */
    private Integer totalCount;


    public void count() {
        // 计算总页数
        int totalPageTemp = this.totalCount / this.pageSize;
        int plus = (this.totalCount % this.pageSize) == 0 ? 0 : 1;
        totalPageTemp += plus;
        // 如果总页数小于0显示第一页
        if (totalPageTemp <= 0) {
            totalPageTemp = 1;
        }
        //如果当前页数小于0，显示第一页
        if(this.currentPage < 0){
            this.currentPage =1;
        }
        //如果当前页数大于总页数，显示最后一页
        if(this.currentPage > totalPageTemp){
             this.currentPage = totalPageTemp;
        }
        this.totalPage = totalPageTemp;
    }

    public PageParameter() {
        this.pageSize = 3;
        this.currentPage = 1;
        this.totalPage = 1;
        this.totalCount = 0;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        count();
    }

    @Override
    public String toString() {
        return "PageParameter{" +
                "pageSize=" + pageSize +
                ", currentPage=" + currentPage +
                ", totalPage=" + totalPage +
                ", totalCount=" + totalCount +
                '}';
    }
}
