package com.company.IntelligentPlatform.common.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.company.IntelligentPlatform.common.service.JBarcode;
import com.company.IntelligentPlatform.common.service.BarcodeEncoder;
import com.company.IntelligentPlatform.common.service.CodabarEncoder;
import com.company.IntelligentPlatform.common.service.Code11Encoder;
import com.company.IntelligentPlatform.common.service.Code128Encoder;
import com.company.IntelligentPlatform.common.service.Code39Encoder;
import com.company.IntelligentPlatform.common.service.Code39ExtEncoder;
import com.company.IntelligentPlatform.common.service.EAN13Encoder;
import com.company.IntelligentPlatform.common.service.EAN8Encoder;
import com.company.IntelligentPlatform.common.service.Interleaved2of5Encoder;
import com.company.IntelligentPlatform.common.service.InvalidAtributeException;
import com.company.IntelligentPlatform.common.service.MSIPlesseyEncoder;
import com.company.IntelligentPlatform.common.service.UPCAEncoder;
import com.company.IntelligentPlatform.common.service.BaseLineTextPainter;
import com.company.IntelligentPlatform.common.service.EAN13TextPainter;
import com.company.IntelligentPlatform.common.service.WideRatioCodedPainter;
import com.company.IntelligentPlatform.common.service.WidthCodedPainter;

import com.company.IntelligentPlatform.common.model.ServiceEntityStringHelper;

@Service
public class DocumentBarCodeService {

    public static final String CODE39 = "CODE39";

    public static final String CODE39EXT = "CODE39EXT";

    public static final String INTERLEAVED25 = "INTERLEAVED25";

    public static final String CODE11 = "CODE11";

    public static final String CODABAR = "CODABAR";

    public static final String MSI = "MSI";

    public static final String UPCA = "UPCA";

    public static final String IND25 = "IND25";

    public static final String MAT25 = "MAT25";

    public static final String CODE93 = "CODE93";

    public static final String EAN13 = "EAN13";

    public static final String EAN8 = "EAN8";

    public static final String UPCE = "UPCE";

    public static final String CODE128 = "CODE128";

    public static final String CODE93EXT = "CODE93EXT";

    public Map<String, String> getBarcodeTypeMap() {
        Map<String, String> mapResult = new HashMap<String, String>();
        mapResult.put(CODABAR, CODABAR);
        mapResult.put(CODE11, CODE11);
        mapResult.put(CODE39, CODE39);
        mapResult.put(CODE39EXT, CODE39EXT);
        mapResult.put(CODE128, CODE128);
        mapResult.put(CODE93EXT, CODE93EXT);
        mapResult.put(EAN13, EAN13);
        mapResult.put(EAN8, EAN8);
        mapResult.put(INTERLEAVED25, INTERLEAVED25);
        mapResult.put(MSI, MSI);
        mapResult.put(UPCA, UPCA);
        mapResult.put(UPCE, UPCE);
        return mapResult;
    }

    public BarcodeEncoder getEncoderInstance(String encodeType)
            throws ServiceBarcodeException {
        if (CODE39.equals(encodeType)) {
            return Code39Encoder.getInstance();
        }
        if (CODE39EXT.equals(encodeType)) {
            return Code39ExtEncoder.getInstance();
        }
        if (INTERLEAVED25.equals(encodeType)) {
            return Interleaved2of5Encoder.getInstance();
        }
        if (CODE11.equals(encodeType)) {
            return Code11Encoder.getInstance();
        }
        if (CODABAR.equals(encodeType)) {
            return CodabarEncoder.getInstance();
        }
        if (MSI.equals(encodeType)) {
            return MSIPlesseyEncoder.getInstance();
        }
        if (UPCA.equals(encodeType)) {
            return UPCAEncoder.getInstance();
        }
        if (IND25.equals(encodeType)) {
            return null;
        }
        if (MAT25.equals(encodeType)) {
            return null;
        }
        if (EAN13.equals(encodeType)) {
            return EAN13Encoder.getInstance();
        }
        if (EAN8.equals(encodeType)) {
            return EAN8Encoder.getInstance();
        }
        if (UPCE.equals(encodeType)) {
            return UPCAEncoder.getInstance();
        }
        if (CODE128.equals(encodeType)) {
            return Code128Encoder.getInstance();
        }
        if (CODE93EXT.equals(encodeType)) {
            return Code39ExtEncoder.getInstance();
        }
        throw new ServiceBarcodeException(
                ServiceBarcodeException.PARA_WRONG_ENCODETYPE, encodeType);
    }

    public byte[] generateBarcodeStreamCore(String encodeType, String barcode)
            throws InvalidAtributeException, ServiceBarcodeException, IOException {
        if (EAN13.equals(encodeType)) {
            // In case EAN13, then validate barcode
            if (barcode == null || barcode.equals(ServiceEntityStringHelper.EMPTYSTRING)) {
                throw new ServiceBarcodeException(
                        ServiceBarcodeException.PARA_WRONG_ENCODETYPE, encodeType);
            }
            if (barcode.length() < 12) {
                throw new ServiceBarcodeException(
                        ServiceBarcodeException.PARA_WRONGLENGTH_BARCODE, barcode.length());
            }
            if (barcode.length() > 12) {
                barcode = barcode.substring(0, 12);
            }
            JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(),
                    WidthCodedPainter.getInstance(),
                    EAN13TextPainter.getInstance());
//			BufferedImage localBufferedImage = localJBarcode
//					.createBarcode(barcode);

            BarcodeEncoder barcodeEncoder = getEncoderInstance(encodeType);
            localJBarcode.setEncoder(barcodeEncoder);
            localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
            localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
            localJBarcode.setShowCheckDigit(false);
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            //TODO REMOVE FOR NOT using JPEGCodec library
//			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(byteOut);
//			encoder.encode(localBufferedImage);
            byte[] bytes = byteOut.toByteArray();
            return bytes;
        }
        throw new ServiceBarcodeException(
                ServiceBarcodeException.PARA_WRONG_ENCODETYPE, encodeType);
    }

    public byte[] generateBarcodeStream(String encodeType, String barcode)
            throws ServiceBarcodeException {
        try {
            return generateBarcodeStreamCore(encodeType, barcode);
        } catch (IOException ex) {
            throw new ServiceBarcodeException(
                    ServiceBarcodeException.PARA_SYSTEM_WRONG, ex.getMessage());
        } catch (InvalidAtributeException ex) {
            throw new ServiceBarcodeException(
                    ServiceBarcodeException.PARA_SYSTEM_WRONG, ex.getMessage());
        }
    }

}
