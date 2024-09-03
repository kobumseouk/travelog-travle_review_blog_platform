package cloud4.team4.travelog.domain.post.dto;

import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.post.entity.Post;
import cloud4.team4.travelog.domain.post.entity.PostPhoto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-24T17:14:17+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post postPostDtoToPost(PostPostDto postPostDto) {
        if ( postPostDto == null ) {
            return null;
        }

        Post post = new Post();

        post.setTitle( postPostDto.getTitle() );
        post.setContent( postPostDto.getContent() );
        post.setPeriodStart( postPostDto.getPeriodStart() );
        post.setPeriodEnd( postPostDto.getPeriodEnd() );

        return post;
    }

    @Override
    public PostResponseDto postToPostResponseDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponseDto postResponseDto = new PostResponseDto();

        postResponseDto.setBoardId( mapBoardToLong( post.getBoard() ) );
        postResponseDto.setMemberId( mapMemberToLong( post.getMember() ) );
        postResponseDto.setPhotos( postPhotoListToStringList( post.getPostPhoto() ) );
        postResponseDto.setPostId( post.getPostId() );
        postResponseDto.setTitle( post.getTitle() );
        postResponseDto.setContent( post.getContent() );
        postResponseDto.setPeriodStart( post.getPeriodStart() );
        postResponseDto.setPeriodEnd( post.getPeriodEnd() );
        postResponseDto.setCreatedAt( post.getCreatedAt() );
        postResponseDto.setEditedAt( post.getEditedAt() );
        postResponseDto.setViews( post.getViews() );
        postResponseDto.setRecommends( post.getRecommends() );
        List<Comment> list1 = post.getComments();
        if ( list1 != null ) {
            postResponseDto.setComments( new ArrayList<Comment>( list1 ) );
        }

        return postResponseDto;
    }

    protected List<String> postPhotoListToStringList(List<PostPhoto> list) {
        if ( list == null ) {
            return null;
        }

        List<String> list1 = new ArrayList<String>( list.size() );
        for ( PostPhoto postPhoto : list ) {
            list1.add( mapPostPhotoToString( postPhoto ) );
        }

        return list1;
    }
}
