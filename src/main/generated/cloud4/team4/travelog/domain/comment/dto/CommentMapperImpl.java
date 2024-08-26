package cloud4.team4.travelog.domain.comment.dto;

import cloud4.team4.travelog.domain.comment.entity.Comment;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-24T17:14:17+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
public class CommentMapperImpl implements CommentMapper {

    @Override
    public CommentResponseDto toResponseDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentResponseDto commentResponseDto = new CommentResponseDto();

        commentResponseDto.setMemberId( mapMemberToLong( comment.getMember() ) );
        commentResponseDto.setContent( comment.getContent() );
        commentResponseDto.setCreatedAt( comment.getCreatedAt() );
        commentResponseDto.setEditedAt( comment.getEditedAt() );

        return commentResponseDto;
    }

    @Override
    public Comment toEntity(CommentRequestDto commentRequestDto) {
        if ( commentRequestDto == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.content( commentRequestDto.getContent() );

        comment.createdAt( java.time.LocalDateTime.now() );

        return comment.build();
    }
}
