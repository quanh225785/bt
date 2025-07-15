package Practice3;

import java.util.*;
import java.time.LocalDate;
import java.util.regex.*;
import java.util.stream.Collectors;

public class QuanLyPhuongTien {
    public static void main(String[] args) {
        DanhSachPhuongTien ds = new DanhSachPhuongTien();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Them phuong tien");
            System.out.println("2. Tim theo so xe");
            System.out.println("3. Tim theo CMND chu xe");
            System.out.println("4. Xoa theo hang");
            System.out.println("5. Hang co nhieu xe nhat");
            System.out.println("6. Sap xep giam dan theo so luong hang");
            System.out.println("7. Thong ke so luong theo loai xe");
            System.out.println("0. Thoat");
            System.out.print("Chon: ");
            int chon = sc.nextInt(); sc.nextLine();

            switch (chon) {
                case 1:
                    ds.themPhuongTien(sc);
                    break;
                case 2:
                    System.out.print("Nhap so xe: ");
                    ds.timTheoSoXe(sc.nextLine());
                    break;
                case 3:
                    System.out.print("Nhap CMND: ");
                    ds.timTheoCMND(sc.nextLine());
                    break;
                case 4:
                    System.out.print("Nhap ten hang: ");
                    ds.xoaTheoHang(sc.nextLine());
                    break;
                case 5:
                    ds.hangNhieuNhat();
                    break;
                case 6:
                    ds.sapXepHang();
                    break;
                case 7:
                    ds.thongKe();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Chon sai");
            }
        }
    }
}

class ChuXe {
    String cmnd, hoTen, email;

    public ChuXe(String cmnd, String hoTen, String email) {
        this.cmnd = cmnd;
        this.hoTen = hoTen;
        this.email = email;
    }

    public String toString() {
        return hoTen + " (CMND: " + cmnd + ", Email: " + email + ")";
    }
}

abstract class PhuongTien {
    String soXe, hang, mau;
    int nam;
    ChuXe chuXe;

    public PhuongTien(String soXe, String hang, int nam, String mau, ChuXe chuXe) {
        this.soXe = soXe;
        this.hang = hang;
        this.nam = nam;
        this.mau = mau;
        this.chuXe = chuXe;
    }

    public String getHang() { return hang; }
    public String getSoXe() { return soXe; }
    public ChuXe getChuXe() { return chuXe; }

    public abstract String loai();

    public String toString() {
        return String.format("[%s] %s - %s, nam %d, mau %s, chu xe: %s", loai(), soXe, hang, nam, mau, chuXe);
    }
}

class Oto extends PhuongTien {
    int soCho;
    String dongCo;

    public Oto(String soXe, String hang, int nam, String mau, ChuXe chuXe, int soCho, String dongCo) {
        super(soXe, hang, nam, mau, chuXe);
        this.soCho = soCho;
        this.dongCo = dongCo;
    }

    public String loai() { return "Oto"; }
}

class XeMay extends PhuongTien {
    int dungTich;

    public XeMay(String soXe, String hang, int nam, String mau, ChuXe chuXe, int dungTich) {
        super(soXe, hang, nam, mau, chuXe);
        this.dungTich = dungTich;
    }

    public String loai() { return "XeMay"; }
}

class XeTai extends PhuongTien {
    double trongTai;

    public XeTai(String soXe, String hang, int nam, String mau, ChuXe chuXe, double trongTai) {
        super(soXe, hang, nam, mau, chuXe);
        this.trongTai = trongTai;
    }

    public String loai() { return "XeTai"; }
}

class DanhSachPhuongTien {
    ArrayList<PhuongTien> ds = new ArrayList<>();

    public void themPhuongTien(Scanner sc) {
        System.out.print("So xe (5 ky tu): ");
        String soXe = sc.nextLine();
        if (soXe.length() != 5 || tonTai(soXe)) return;

        System.out.print("Hang (Honda, Yamaha, Toyota, Suzuki): ");
        String hang = sc.nextLine();
        List<String> hangChapNhan = Arrays.asList("Honda", "Yamaha", "Toyota", "Suzuki");
        if (!hangChapNhan.contains(hang)) return;

        System.out.print("Nam san xuat (>=2000): ");
        int nam = sc.nextInt(); sc.nextLine();
        if (nam < 2000 || nam > LocalDate.now().getYear()) return;

        System.out.print("Mau: ");
        String mau = sc.nextLine();

        System.out.print("CMND (12 so): ");
        String cmnd = sc.nextLine();
        if (!cmnd.matches("\\d{12}")) return;

        System.out.print("Ho ten: ");
        String hoTen = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();
        if (!email.matches("^\\S+@\\S+\\.\\S+$")) return;

        ChuXe chuXe = new ChuXe(cmnd, hoTen, email);

        System.out.println("Loai (1.Oto 2.XeMay 3.XeTai): ");
        int loai = sc.nextInt(); sc.nextLine();

        PhuongTien p = null;
        switch (loai) {
            case 1:
                System.out.print("So cho: ");
                int cho = sc.nextInt(); sc.nextLine();
                System.out.print("Dong co: ");
                String dongCo = sc.nextLine();
                p = new Oto(soXe, hang, nam, mau, chuXe, cho, dongCo);
                break;
            case 2:
                System.out.print("Dung tich: ");
                int dt = sc.nextInt(); sc.nextLine();
                p = new XeMay(soXe, hang, nam, mau, chuXe, dt);
                break;
            case 3:
                System.out.print("Trong tai: ");
                double tt = sc.nextDouble(); sc.nextLine();
                p = new XeTai(soXe, hang, nam, mau, chuXe, tt);
                break;
        }

        if (p != null) {
            ds.add(p);
            System.out.println("Them thanh cong.");
        }
    }

    private boolean tonTai(String soXe) {
        for (PhuongTien p : ds)
            if (p.getSoXe().equalsIgnoreCase(soXe)) return true;
        return false;
    }

    public void timTheoSoXe(String s) {
        for (PhuongTien p : ds)
            if (p.getSoXe().equalsIgnoreCase(s)) System.out.println(p);
    }

    public void timTheoCMND(String cmnd) {
        for (PhuongTien p : ds)
            if (p.getChuXe().cmnd.equals(cmnd)) System.out.println(p);
    }

    public void xoaTheoHang(String hang) {
        ds.removeIf(p -> p.getHang().equalsIgnoreCase(hang));
        System.out.println("Da xoa cac xe hang " + hang);
    }

    public void hangNhieuNhat() {
        Map<String, Long> count = ds.stream()
                .collect(Collectors.groupingBy(PhuongTien::getHang, Collectors.counting()));
        count.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(e -> System.out.println("Hang nhieu nhat: " + e.getKey() + " - " + e.getValue() + " xe"));
    }

    public void sapXepHang() {
        Map<String, Long> count = ds.stream()
                .collect(Collectors.groupingBy(PhuongTien::getHang, Collectors.counting()));
        count.entrySet().stream()
                .sorted((a, b) -> Long.compare(b.getValue(), a.getValue()))
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue()));
    }

    public void thongKe() {
        Map<String, Long> count = ds.stream()
                .collect(Collectors.groupingBy(PhuongTien::loai, Collectors.counting()));
        count.forEach((k, v) -> System.out.println(k + ": " + v));
    }
}
