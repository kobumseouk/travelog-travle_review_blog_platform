package cloud4.team4.travelog.domain.post.dto;

import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.entity.PostPhoto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cloud4.team4.travelog.domain.member.entity.Member;

@Mapper(componentModel = "spring")
public interface PostMapper {
  @Mapping(target = "member", ignore = true)
  @Mapping(target = "board", ignore = true)
  @Mapping(target = "postPhoto", ignore = true)   // member, board, postPhoto 필드 매핑 제외
  Post postPostDtoToPost(PostPostDto postPostDto);

  @Mapping(source = "board", target="boardId")
  @Mapping(source = "member", target="memberId")
  @Mapping(source = "postPhoto.imagePath", target = "photoPath")
  @Mapping(source = "postPhoto.position", target = "photoPosition")
  PostResponseDto postToPostResponseDto(Post post);

  default Long mapMemberToLong(Member member) {
    return member != null ? member.getId() : null;
  }
  default Long mapBoardToLong(Board board) {
    return board != null ? board.getId() : null;
  }

  /*---------추가(디버깅)---------*/
  default String mapPostPhotoToString(PostPhoto postPhoto) {
    return postPhoto != null ? postPhoto.getImagePath() : null;
  }

}
