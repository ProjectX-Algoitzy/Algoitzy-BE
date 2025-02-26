package org.example.schedule.service;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.api_response.exception.GeneralException;
import org.example.domain.board.Board;
import org.example.domain.board.service.CoreBoardService;
import org.example.domain.institution.Institution;
import org.example.domain.institution.service.CoreInstitutionService;
import org.example.util.RedisUtils;
import org.example.util.ValueUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CreateViewCountService {

  private final CoreInstitutionService coreInstitutionService;
  private final CoreBoardService coreBoardService;
  private final RedisUtils redisUtils;

  /**
   * 조회수 동기화
   */
  public void syncViewCount() {
    // 기관 조회수 반영
    Set<String> keySet = redisUtils.findAllKeysByPattern(ValueUtils.INSTITUTION_VIEW_COUNT_KEY + "*");
    for (String key : keySet) {
      int viewCount = Integer.parseInt(redisUtils.getValue(key));
      Long institutionId = Long.parseLong(key.substring(key.lastIndexOf(":") + 1));
      try {
        Institution institution = coreInstitutionService.findById(institutionId);
        institution.syncViewCount(viewCount);
      } catch (GeneralException e) {
        log.info("삭제된 기관입니다.");
      }
    }
    redisUtils.delete(keySet);

    // 게시글 조회수 반영
    keySet = redisUtils.findAllKeysByPattern(ValueUtils.BOARD_VIEW_COUNT_KEY + "*");
    for (String key : keySet) {
      int viewCount = Integer.parseInt(redisUtils.getValue(key));
      Long boardId = Long.parseLong(key.substring(key.lastIndexOf(":") + 1));
      try {
        Board board = coreBoardService.findById(boardId);
        board.syncViewCount(viewCount);
      } catch (GeneralException e) {
        log.info("삭제된 게시글입니다.");
      }
    }
    redisUtils.delete(keySet);
  }
}
