package cn.exam.util;

import com.github.pagehelper.PageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResult<T> {
    private long count;
    private T list;

    public static <T> PageResult<List<T>> toPageData(List<T> list){
        PageResult<List<T>> pageResult = new PageResult<>();
        PageInfo<T> pageInfo = new PageInfo<>(list);
        pageResult.setCount(pageInfo.getTotal());
        pageResult.setList(list);
        return pageResult;
    }
}
