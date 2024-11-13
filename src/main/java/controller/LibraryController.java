package controller;

import api.library.LibraryData;
import com.loc.material.api.LocalClassificationService;
import com.loc.material.api.Material;
import com.loc.material.api.MaterialType;
import domain.core.ClassificationApiFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import util.DateUtil;

import java.util.Date;

import static java.lang.String.format;

@RestController
public class LibraryController {
    @PostMapping(value = "/clear")
    public void clear() {
        LibraryData.deleteAll();
    }

    @PostMapping(value = "/use_local_classification")
    public void useLocalClassificationService() {
        ClassificationApiFactory.setService(new LocalClassificationService());
    }

    @PostMapping(value = "/reset_current_date")
    public void resetCurrentDate() {
        DateUtil.resetClock();
    }

    @PostMapping(value = "/current_date")
    public void setCurrentDate(@RequestBody Date date) {
        DateUtil.fixClockAt(date);
    }

    @PostMapping(value = "/materials")
    public void addMaterial(@RequestBody MaterialRequest materialRequest) {
        var materialType = MaterialType.valueOf(materialRequest.format());
        if (materialType == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, format("invalid material materialType %s", materialType));

        var material = new Material(
           materialRequest.sourceId(),
           materialRequest.author(),
           materialRequest.title(),
           materialRequest.classification(),
           materialType,
           materialRequest.year()
        );
        ((LocalClassificationService) ClassificationApiFactory.getService())
          .addBook(material);
    }

}
