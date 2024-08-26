package cloud4.team4.travelog.domain.post.dto;

import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.entity.PostPhoto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cloud4.team4.travelog.domain.member.entity.Member;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PostMapper {
  @Mapping(target = "member", ignore = true)
  @Mapping(target = "board", ignore = true)
  @Mapping(target = "postPhotos", ignore = true)   // member, board, PostPhoto 필드 매핑 제외
  Post postPostDtoToPost(PostPostDto postPostDto);

  @Mapping(source = "board", target="boardId")
  @Mapping(source = "member", target="memberId")
  @Mapping(source = "postPhotos", target = "photos")
  PostResponseDto postToPostResponseDto(Post post);

  default Long mapMemberToLong(Member member) {
    return member != null ? member.getId() : null;
  }
  default Long mapBoardToLong(Board board) {
    return board != null ? board.getId() : null;
  }

  /*---------추가(디버깅)---------*/
  default List<String> mapPostPhotos(List<PostPhoto> postPhotos) {
    if (postPhotos == null) {
      return null;
    }
    return postPhotos.stream()
        .map(PostPhoto::getBase64Image)
        .collect(Collectors.toList());
  }


}
