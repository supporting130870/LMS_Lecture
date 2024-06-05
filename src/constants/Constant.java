package constants;

public class Constant {
    public static class MainFrame
    {
        public final static long VERSIONID = 1L;
        public final static int X = 200;
        public final static int Y = 100;
        public final static int WIDTH = 1400;
        public final static int HEIGHT = 800;

    }
    public static class VControlPanel
    {
        public final static long VERSIONID = 1L;
        public final static String leftButton = "<<<";
        public final static String rightButton = ">>>";

    }
    public static class VIndexPanel
    {
        public final static long VERSIONID = 1L;
        public static final String home = "root";
    }

    public static class VLectureTable
    {
        public final static long VERSIONID = 1L;
        public enum EHeader
        {
            eID("아이디"),
            eName("강좌명"),
            eLecturer("강사명"),
            eCredit("학점"),
            eTime("시간");




            public String title;
            private EHeader(String title)
            {
                this.title = title;
            }
            public String getTitle()
            {
                return this.title;
            }


        }
    }

    public static class IndexTable
    {
        public final static long VERSIONID = 1L;
        public enum EHeader
        {
            eID("아이디"),
            eName("이름");



            public String title;
            private EHeader(String title)
            {
                this.title = title;
            }
            public String getTitle()
            {
                return this.title;
            }


        }


    }
    public static class VSelectionPanel
    {
        public final static long VERSIONID = 1L;
    }
    public static class VSugangSincheong
    {
        public final static long VERSIONID = 1L;
    }
}
