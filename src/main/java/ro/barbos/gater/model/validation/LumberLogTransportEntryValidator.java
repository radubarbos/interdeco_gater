package ro.barbos.gater.model.validation;

import ro.barbos.gater.dao.LumberLogDAO;
import ro.barbos.gater.dto.LumberLogFilterDTO;
import ro.barbos.gater.model.LumberLog;
import ro.barbos.gater.model.LumberLogTransportEntry;

import java.util.List;

/**
 * Created by radu on 8/23/2016.
 */
public class LumberLogTransportEntryValidator {

    public String validate(LumberLogTransportEntry entry) {
        String errMsg = null;
        Long id = entry.getId();
        Long supplierId = entry.getSupplierId();
        Long certificateId = entry.getCertificateId();
        if (id == null) {
            errMsg = "Receptia nu a fost creata";
        } else if (supplierId == null) {
            errMsg = "Nu sa selectat un furnizor";
        } else if (certificateId == null) {
            errMsg = "Nu sa selectat un aviz";
        }
        if (errMsg != null) {
            return errMsg;
        }
        LumberLogFilterDTO filter = new LumberLogFilterDTO();
        filter.setTransportEntryId(entry.getId());
        filter.setStatus(-1);
        List<LumberLog> lumberLogs = new LumberLogDAO().findAll(filter);
        if (lumberLogs.isEmpty()) {
            errMsg = "Nu sa atasat nici un bustean la receptie.";
        }
        if (lumberLogs.size() != entry.getLumberLogCount()) {
            errMsg = "Eroare la numarul bustenilor. Redeschide applicatia.";
        }
        for (LumberLog lumberLog : lumberLogs) {
            if (lumberLog.getSupplierId() != supplierId.longValue() || lumberLog.getTransportCertifiateId() != certificateId.longValue()) {
                errMsg = "Uni busteni au date invalide. Reseteaza furnizorul si avizul.";
                break;
            }
        }
        return errMsg;
    }
}
