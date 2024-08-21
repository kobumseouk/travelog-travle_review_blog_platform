package cloud4.team4.travelog.domain.post.Mapper;

import cloud4.team4.travelog.domain.post.dto.PostPostDto;
import cloud4.team4.travelog.domain.post.dto.PostResponseDto;
import cloud4.team4.travelog.domain.post.dto.Post_PhotoDto;
import cloud4.team4.travelog.domain.post.dto.Post_PhotoResponseDto;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.entity.Post_Photo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

  Post postPostDtoToPost(PostPostDto postPostDto);
  PostResponseDto postToPostResponseDto(Post createdPost);

//
//  @Mapping(target = "postId", ignore = true)
//  Post_Photo post_PhotoDtoToPost_Photo(Post_PhotoDto postPhotoDto);
//
//  @Mapping(source = "post", target = "postId")
//  Post_PhotoResponseDto post_PhotoToPost_PhotoResponseDto(Post_Photo postPhoto);

}
