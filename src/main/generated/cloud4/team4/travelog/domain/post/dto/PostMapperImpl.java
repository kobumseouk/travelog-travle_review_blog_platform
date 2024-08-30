package cloud4.team4.travelog.domain.post.dto;

import cloud4.team4.travelog.domain.board.entity.Board;
import cloud4.team4.travelog.domain.comment.entity.Comment;
import cloud4.team4.travelog.domain.member.entity.Member;
import cloud4.team4.travelog.domain.post.entity.Post;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-30T19:40:39+0900",
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

        post.setId( postPostDto.getId() );
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
        postResponseDto.setMemberName( postMemberName( post ) );
        postResponseDto.setBoardCategory( postBoardBoardCategory( post ) );
        postResponseDto.setPhotos( mapPostPhotos( post.getPostPhotos() ) );
        postResponseDto.setId( post.getId() );
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

    @Override
    public PostUpdateDto postToPostUpdateDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostUpdateDto postUpdateDto = new PostUpdateDto();

        postUpdateDto.setId( post.getId() );
        postUpdateDto.setTitle( post.getTitle() );
        postUpdateDto.setContent( post.getContent() );
        postUpdateDto.setPeriodStart( post.getPeriodStart() );
        postUpdateDto.setPeriodEnd( post.getPeriodEnd() );

        return postUpdateDto;
    }

    private String postMemberName(Post post) {
        if ( post == null ) {
            return null;
        }
        Member member = post.getMember();
        if ( member == null ) {
            return null;
        }
        String name = member.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String postBoardBoardCategory(Post post) {
        if ( post == null ) {
            return null;
        }
        Board board = post.getBoard();
        if ( board == null ) {
            return null;
        }
        String boardCategory = board.getBoardCategory();
        if ( boardCategory == null ) {
            return null;
        }
        return boardCategory;
    }
}
