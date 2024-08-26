package cloud4.team4.travelog.domain.post.dto;

import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.entity.PostPhoto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import cloud4.team4.travelog.domain.member.entity.Member;

import java.util.Base64;

@Mapper(componentModel = "spring")
public interface PostMapper {
  @Mapping(target = "member", ignore = true)
  @Mapping(target = "board", ignore = true)
  @Mapping(target = "photos", ignore = true)
  @Mapping(target = "photoPositions", ignore = true)   // member, board, Photor관련 필드 매핑 제외
  Post postPostDtoToPost(PostPostDto postPostDto);

  @Mapping(source = "board.id", target="boardId")
  @Mapping(source = "member.id", target="memberId")
  @Mapping(target = "photo", expression = "java(postPhotoToPostPhotoDto(post.getPostPhoto()))")
  PostResponseDto postToPostResponseDto(Post post);

  default Long mapMemberToLong(Member member) {
    return member != null ? member.getId() : null;
  }
  default Long mapBoardToLong(Board board) {
    return board != null ? board.getId() : null;
  }

  /*---------추가(디버깅)---------*/
  @Mapping(target = "base64Image", expression = "java(encodeBase64Image(postPhoto.getImageData()))")
  PostPhotoDto postPhotoToPostPhotoDto(PostPhoto postPhoto);

  default String encodeBase64Image(byte[] imageData) {
    return imageData != null ? Base64.getEncoder().encodeToString(imageData) : null;
  }


}
