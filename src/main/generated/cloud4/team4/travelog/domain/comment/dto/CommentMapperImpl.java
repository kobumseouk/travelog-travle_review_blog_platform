package cloud4.team4.travelog.domain.comment.dto;

import cloud4.team4.travelog.domain.comment.entity.Comment;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-23T22:08:40+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
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
        commentResponseDto.setCreated_at( comment.getCreated_at() );
        commentResponseDto.setEdited_at( comment.getEdited_at() );

        return commentResponseDto;
    }

    @Override
    public Comment toEntity(CommentRequestDto commentRequestDto) {
        if ( commentRequestDto == null ) {
            return null;
        }

        Comment.CommentBuilder comment = Comment.builder();

        comment.content( commentRequestDto.getContent() );

        comment.created_at( java.time.LocalDateTime.now() );

        return comment.build();
    }
}
