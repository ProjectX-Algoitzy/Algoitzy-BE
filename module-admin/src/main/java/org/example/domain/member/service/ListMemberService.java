package org.example.domain.member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.domain.member.controller.request.SearchMemberRequest;
import org.example.domain.member.controller.response.ListMemberDto;
import org.example.domain.member.controller.response.ListMemberResponse;
import org.example.domain.member.repository.ListMemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ListMemberService {

  private final ListMemberRepository listMemberRepository;

  /**
   * 관리자 목록 조회
   */
  public ListMemberResponse getAdminMemberList() {
    List<ListMemberDto> memberList = listMemberRepository.getAdminMemberList();
    return ListMemberResponse.builder()
      .memberList(memberList)
      .totalCount(memberList.size())
      .build();
  }

  /**
   * 스터디원 목록 조회
   */
  public ListMemberResponse getUserMemberList(SearchMemberRequest request) {
    Page<ListMemberDto> page = listMemberRepository.getUserMemberList(request);
    return ListMemberResponse.builder()
      .memberList(page.getContent())
      .totalCount(page.getTotalElements())
      .build();
  }
}
