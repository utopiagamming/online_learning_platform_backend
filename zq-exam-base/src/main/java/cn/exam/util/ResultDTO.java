package cn.exam.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 封装业务成返回数据
 *
 * @param <T>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultDTO<T> implements Serializable {


    private static final long serialVersionUID = -4238137936601328359L;

    @Builder.Default
    private String code = SystemCode.SYS_EXCEPTION_CODE;

    @Builder.Default
    private String description = SystemCode.SYS_EXCEPTION_MSG;

    private T result;

    public void buildReturnCode(String code,String description){
        this.code = code;
        this.description = description;
    }

    public static<T> ResultDTO<T> buildReturnSuccess(){
        ResultDTO<T> resultDTO= new ResultDTO<>();
        resultDTO.setCode(SystemCode.RET_CODE_SUCC);
        resultDTO.setDescription(SystemCode.RET_MSG_SUCC);
        return resultDTO;
    }

    public static<T> ResultDTO<T> buildReturnSuccess(T result){
        ResultDTO<T> resultDTO= new ResultDTO<>();
        resultDTO.setCode(SystemCode.RET_CODE_SUCC);
        resultDTO.setDescription(SystemCode.RET_MSG_SUCC);
        resultDTO.setResult(result);
        return resultDTO;
    }

    public ResultDTO(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
