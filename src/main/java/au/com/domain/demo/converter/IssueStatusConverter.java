package au.com.domain.demo.converter;

import au.com.domain.demo.tracker.entity.Status;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Objects;

@Converter
public class IssueStatusConverter implements AttributeConverter<Status, String> {

    @Nullable
    @Override
    public String convertToDatabaseColumn(Status attribute){
        if (Objects.isNull(attribute)) {
            return null;
        }

        return attribute.name().toLowerCase();
    }

    @Nullable
    @Override
    public Status convertToEntityAttribute(String dbData) {
        if (StringUtils.isEmpty(dbData)) {
            return null;
        }

        return Status.valueOf(dbData.toUpperCase());
    }

}
