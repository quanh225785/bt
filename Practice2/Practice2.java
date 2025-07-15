package Practice2;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Practice2 {
    static HangHoa[] danhSach = new HangHoa[100];
    static int soLuong = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int chon;

        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Them hang hoa");
            System.out.println("2. Hien thi danh sach");
            System.out.println("0. Thoat");
            System.out.print("Chon chuc nang: ");
            chon = Integer.parseInt(sc.nextLine());

            switch (chon) {
                case 1:
                    themHang(sc);
                    break;
                case 2:
                    hienThiTatCa();
                    break;
                case 0:
                    System.out.println("Ket thuc chuong trinh.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);
    }

    public static void themHang(Scanner sc) {
        if (soLuong >= danhSach.length) {
            System.out.println("Danh sach da day!");
            return;
        }

        System.out.println("Chon loai hang:");
        System.out.println("1. Thuc pham");
        System.out.println("2. Dien tu");
        System.out.println("3. Gia dung");
        System.out.print("Nhap lua chon: ");
        int loai = Integer.parseInt(sc.nextLine());

        System.out.print("Ma hang: ");
        String ma = sc.nextLine();
        if (kiemTraTrungMa(ma)) {
            System.out.println("Ma bi trung!");
            return;
        }

        System.out.print("Ten hang: ");
        String ten = sc.nextLine();
        System.out.print("So luong: ");
        int so = Integer.parseInt(sc.nextLine());
        System.out.print("Don gia: ");
        double gia = Double.parseDouble(sc.nextLine());

        HangHoa hh = null;

        if (loai == 1) {
            System.out.print("Nha cung cap: ");
            String ncc = sc.nextLine();
            System.out.print("Ngay san xuat (yyyy-mm-dd): ");
            LocalDate nsx = LocalDate.parse(sc.nextLine());
            System.out.print("Ngay het han (yyyy-mm-dd): ");
            LocalDate hsd = LocalDate.parse(sc.nextLine());
            hh = new ThucPham(ma, ten, so, gia, nsx, hsd, ncc);
        } else if (loai == 2) {
            System.out.print("Thoi gian bao hanh (thang): ");
            int bh = Integer.parseInt(sc.nextLine());
            System.out.print("Cong suat (KW): ");
            double cs = Double.parseDouble(sc.nextLine());
            hh = new DienTu(ma, ten, so, gia, bh, cs);
        } else if (loai == 3) {
            System.out.print("Nha san xuat: ");
            String nsx = sc.nextLine();
            System.out.print("Ngay nhap kho (yyyy-mm-dd): ");
            LocalDate ngay = LocalDate.parse(sc.nextLine());
            hh = new GiaDung(ma, ten, so, gia, nsx, ngay);
        } else {
            System.out.println("Loai hang khong hop le!");
            return;
        }

        danhSach[soLuong++] = hh;
        System.out.println("Da them hang thanh cong!");
    }

    public static boolean kiemTraTrungMa(String ma) {
        for (int i = 0; i < soLuong; i++) {
            if (danhSach[i].getMa().equalsIgnoreCase(ma)) {
                return true;
            }
        }
        return false;
    }

    public static void hienThiTatCa() {
        if (soLuong == 0) {
            System.out.println("Danh sach rong.");
            return;
        }
        for (int i = 0; i < soLuong; i++) {
            HangHoa h = danhSach[i];
            System.out.println(h.thongTin());
            System.out.println("VAT: " + h.tinhVAT());
            System.out.println("Danh gia: " + h.danhGiaTieuThu());
            System.out.println("-----------------------------");
        }
    }
}

// Lop cha HangHoa
abstract class HangHoa {
    protected String ma;
    protected String ten;
    protected int soLuong;
    protected double donGia;

    public HangHoa(String ma, String ten, int soLuong, double donGia) {
        this.ma = ma;
        this.ten = ten;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public abstract double tinhVAT();
    public abstract String danhGiaTieuThu();

    public String thongTin() {
        return String.format("Ma: %s | Ten: %s | So luong: %d | Don gia: %.0f", ma, ten, soLuong, donGia);
    }

    public String getMa() {
        return ma;
    }
}

// Lop con: ThucPham
class ThucPham extends HangHoa {
    private LocalDate ngaySX;
    private LocalDate ngayHH;
    private String nhaCungCap;

    public ThucPham(String ma, String ten, int soLuong, double donGia,
                    LocalDate ngaySX, LocalDate ngayHH, String nhaCungCap) {
        super(ma, ten, soLuong, donGia);
        this.ngaySX = ngaySX;
        this.ngayHH = ngayHH;
        this.nhaCungCap = nhaCungCap;
    }

    @Override
    public double tinhVAT() {
        return 0.05 * donGia * soLuong;
    }

    @Override
    public String danhGiaTieuThu() {
        if (soLuong > 0 && ngayHH.isBefore(LocalDate.now())) {
            return "Kho ban (da het han)";
        }
        return "Khong danh gia";
    }
}

// Lop con: DienTu
class DienTu extends HangHoa {
    private int thoiGianBaoHanh;
    private double congSuatKW;

    public DienTu(String ma, String ten, int soLuong, double donGia,
                  int baoHanh, double congSuat) {
        super(ma, ten, soLuong, donGia);
        this.thoiGianBaoHanh = baoHanh;
        this.congSuatKW = congSuat;
    }

    @Override
    public double tinhVAT() {
        return 0.1 * donGia * soLuong;
    }

    @Override
    public String danhGiaTieuThu() {
        return (soLuong < 3) ? "Duoc xem la da ban" : "Khong danh gia";
    }
}

// Lop con: GiaDung
class GiaDung extends HangHoa {
    private String nhaSanXuat;
    private LocalDate ngayNhapKho;

    public GiaDung(String ma, String ten, int soLuong, double donGia,
                   String nhaSanXuat, LocalDate ngayNhap) {
        super(ma, ten, soLuong, donGia);
        this.nhaSanXuat = nhaSanXuat;
        this.ngayNhapKho = ngayNhap;
    }

    @Override
    public double tinhVAT() {
        return 0.1 * donGia * soLuong;
    }

    @Override
    public String danhGiaTieuThu() {
        long soNgayLuuKho = ChronoUnit.DAYS.between(ngayNhapKho, LocalDate.now());
        if (soLuong > 50 && soNgayLuuKho > 10) {
            return "Ban cham";
        }
        return "Khong danh gia";
    }
}
