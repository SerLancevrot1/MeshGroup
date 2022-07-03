package ru.meshGroupTestTask.utils;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagedDto<T> {
    private List<T> data;
    private Long pageCount;
    private Long totalCount;
}
