package com.estsoft.paldotourism.dto.qna.article;

import java.util.List;
import java.util.stream.IntStream;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PageResponseDTO {

  private List<ArticleResponseDTO> dtoList;

  private int totalPage;

  private int page;

  private int size;

  private int start,end;

  private boolean prev, next;

  private List<Integer> pageList;

  public PageResponseDTO(Page<ArticleResponseDTO> response){
    dtoList = response.getContent();

    totalPage = response.getTotalPages();

    makePageList(response.getPageable());
  }

  private void makePageList(Pageable pageable){
    this.page = pageable.getPageNumber() + 1;
    this.size = pageable.getPageSize();;

    int tempEnd = (int)(Math.ceil(page/10.0)) * 5;

    start = tempEnd - 4;

    prev = start > 1;

    end = Math.min(totalPage, tempEnd);

    next = totalPage > tempEnd;

    pageList = IntStream.rangeClosed(start, end).boxed().toList();
  }

}
