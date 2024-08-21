package cloud4.team4.travelog.domain.post.Mapper;

import cloud4.team4.travelog.domain.post.dto.PostPostDto;
import cloud4.team4.travelog.domain.post.dto.PostResponseDto;
import cloud4.team4.travelog.domain.post.entity.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

  Post postPostDtoToPost(PostPostDto postPostDto);
  PostResponseDto postToPostResponseDto(Post createdPost);

//
//  @Mapping(target = "postId", ignore = true)
//  PostPhoto postPhotoDtoToPostPhoto(PostPhotoDto postPhotoDto);
//
//  @Mapping(source = "post", target = "postId")
//  PostPhotoResponseDto postPhotoToPostPhotoResponseDto(PostPhoto postPhoto);

}
