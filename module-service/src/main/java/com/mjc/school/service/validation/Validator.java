package com.mjc.school.service.validation;

import com.mjc.school.service.errorsexceptions.Errors;
import com.mjc.school.service.errorsexceptions.ValidatorException;
import org.springframework.stereotype.Component;


@Component
public class Validator {
    private static final int NEWS_TITLE_MIN = 5;
    private static final int NEWS_TITLE_MAX = 30;

    public void validateNewsId(Long newsId) {
        if (newsId==null || newsId<1) {
            throw new ValidatorException(Errors.ERROR_NEWS_ID_VALUE.getErrorData(String.valueOf(newsId), false));
        }
    }

    public void validateSortField(String sortField) {
        if (sortField != null) {
            if (sortField.equals("createdate") || sortField.equals("lastupdatedate")) {
                return;
            }
            throw new ValidatorException(Errors.ERROR_SORT_FIELD.getErrorData(sortField, false));
        }
    }

    public void validateSortOrder(String sortOrder) {
        if (sortOrder != null) {
            if (sortOrder.equals("asc") || sortOrder.equals("desc")) {
                return;
            }
            throw new ValidatorException(Errors.ERROR_SORT_ORDER.getErrorData(sortOrder, false));
        }
    }
}