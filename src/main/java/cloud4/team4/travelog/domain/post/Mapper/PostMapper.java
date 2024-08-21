package cloud4.team4.Travelog.Post.Mapper;

import cloud4.team4.Travelog.Post.Dto.PostPostDto;
import cloud4.team4.Travelog.Post.Dto.PostResponseDto;
import cloud4.team4.Travelog.Post.Dto.Post_PhotoDto;
import cloud4.team4.Travelog.Post.Dto.Post_PhotoResponseDto;
import cloud4.team4.Travelog.Post.Entity.Post;
import cloud4.team4.Travelog.Post.Entity.Post_Photo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

  Post postPostDtoToPost(PostPostDto postPostDto);
  PostResponseDto postToPostResponseDto(Post createdPost);


  @Mapping(target = "postId", ignore = true)
  Post_Photo post_PhotoDtoToPost_Photo(Post_PhotoDto postPhotoDto);

  @Mapping(source = "post", target = "postId")
  Post_PhotoResponseDto post_PhotoToPost_PhotoResponseDto(Post_Photo postPhoto);

}
