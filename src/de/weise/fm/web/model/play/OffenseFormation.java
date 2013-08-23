package de.weise.fm.web.model.play;

public class OffenseFormation extends Formation {

    public OffenseFormation(String name) {
        super(name);
        addPosition(Position.C, FieldPosition.C);
        addPosition(Position.RG, FieldPosition.RG);
        addPosition(Position.LG, FieldPosition.LG);
        addPosition(Position.RT, FieldPosition.RT);
        addPosition(Position.LT, FieldPosition.LT);
        createDefault();
    }

    protected void createDefault() {
        addPosition(Position.QB, FieldPosition.QB);
        addPosition(Position.FB, FieldPosition.FB);
        addPosition(Position.HB, FieldPosition.HB);
        addPosition(Position.TE, FieldPosition.TE);
        addPosition(Position.WR, FieldPosition.WR_RIGHT);
        addPosition(Position.WR, FieldPosition.WR_LEFT);
    }

    @Override
    public void removePosition(Position position, FieldPosition fieldPosition) {
        if(!(Position.C.equals(position) || Position.QB.equals(position)
                || Position.RT.equals(position) || Position.LT.equals(position)
                || Position.RG.equals(position) || Position.LG.equals(position))) {
            super.removePosition(position, fieldPosition);
        }
    }

    @Override
    public void movePosition(Position position, FieldPosition oldFieldPosition, FieldPosition newFieldPosition) {
        if(!(Position.C.equals(position)
                || Position.RT.equals(position) || Position.LT.equals(position)
                || Position.RG.equals(position) || Position.LG.equals(position))) {
            super.movePosition(position, oldFieldPosition, newFieldPosition);
        }
    }

    @Override
    public boolean validate() {
        if(!super.validate()) {
            return false;
        }

        int losCount = 0;
        for(PositionWrapper pw : getPositions()) {
            int height = pw.getFieldPosition().getHeight();
            if(height > 0) { // player over LoS
                return false;
            }
            if(height == 0) {
                losCount++;
            }
        }

        return losCount == 7; // exactly 7 players at LoS
    }

    @Override
    public boolean isOffenseFormation() {
        return true;
    }

    public static class I_Form extends OffenseFormation {
        public I_Form() {
            super("I-Form");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.QB, FieldPosition.QB);
            addPosition(Position.FB, FieldPosition.FB);
            addPosition(Position.HB, FieldPosition.HB);
            addPosition(Position.TE, FieldPosition.TE);
            addPosition(Position.WR, FieldPosition.WR_RIGHT);
            addPosition(Position.WR, FieldPosition.WR_LEFT);
        }
    }

    public static class Strong extends I_Form {
        public Strong() {
            setName("Strong");
        }

        @Override
        protected void createDefault() {
            super.createDefault();
            movePosition(Position.FB, new FieldPosition(-6, 3));
        }
    }

    public static class Weak extends I_Form {
        public Weak() {
            setName("Weak");
        }

        @Override
        protected void createDefault() {
            super.createDefault();
            movePosition(Position.FB, new FieldPosition(-6, -3));
        }
    }

    public static class SingleBack extends OffenseFormation {
        public SingleBack() {
            super("Single-Back");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.QB, FieldPosition.QB);
            addPosition(Position.HB, FieldPosition.HB);
            addPosition(Position.TE, FieldPosition.TE);
            addPosition(Position.WR, FieldPosition.WR_RIGHT);
            addPosition(Position.WR, FieldPosition.WR_LEFT);
            addPosition(Position.WR, new FieldPosition(-1, -15));
        }
    }

    public static class HBack extends OffenseFormation {
        public HBack() {
            super("H-Back");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.QB, FieldPosition.QB);
            addPosition(Position.HB, FieldPosition.HB);
            addPosition(Position.TE, FieldPosition.TE);
            addPosition(Position.TE, new FieldPosition(-1, -9));
            addPosition(Position.WR, FieldPosition.WR_RIGHT);
            addPosition(Position.WR, FieldPosition.WR_LEFT);
        }
    }

    public static class SplitBacks extends OffenseFormation {
        public SplitBacks() {
            super("Split Backs");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.QB, FieldPosition.QB);
            addPosition(Position.HB, new FieldPosition(-6, 3));
            addPosition(Position.HB, new FieldPosition(-6, -3));
            addPosition(Position.TE, FieldPosition.TE);
            addPosition(Position.WR, FieldPosition.WR_RIGHT);
            addPosition(Position.WR, FieldPosition.WR_LEFT);
        }
    }

    public static class Shotgun extends OffenseFormation {
        public Shotgun() {
            super("Shotgun");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.QB, new FieldPosition(-7, 0));
            addPosition(Position.HB, new FieldPosition(-7, -3));
            addPosition(Position.TE, FieldPosition.TE);
            addPosition(Position.WR, FieldPosition.WR_RIGHT);
            addPosition(Position.WR, FieldPosition.WR_LEFT);
            addPosition(Position.WR, new FieldPosition(-1, -15));
        }
    }

    public static class ShotgunMaxProtect extends OffenseFormation {
        public ShotgunMaxProtect() {
            super("Shotgun Max Protect");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.QB, new FieldPosition(-7, 0));
            addPosition(Position.FB, new FieldPosition(-7, -3));
            addPosition(Position.HB, new FieldPosition(-7, 3));
            addPosition(Position.TE, FieldPosition.TE);
            addPosition(Position.WR, FieldPosition.WR_RIGHT);
            addPosition(Position.WR, FieldPosition.WR_LEFT);
        }
    }

    public static class Pistol extends OffenseFormation {
        public Pistol() {
            super("Pistol");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.QB, new FieldPosition(-5, 0));
            addPosition(Position.HB, new FieldPosition(-8, 0));
            addPosition(Position.TE, FieldPosition.TE);
            addPosition(Position.WR, FieldPosition.WR_RIGHT);
            addPosition(Position.WR, FieldPosition.WR_LEFT);
            addPosition(Position.WR, new FieldPosition(-1, -15));
        }
    }

    public static class GoalLine extends OffenseFormation {
        public GoalLine() {
            super("Goal Line");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.QB, FieldPosition.QB);
            addPosition(Position.FB, FieldPosition.FB);
            addPosition(Position.HB, FieldPosition.HB);
            addPosition(Position.TE, FieldPosition.TE);
            addPosition(Position.TE, new FieldPosition(0, -9));
            addPosition(Position.TE, new FieldPosition(-1, 12));
        }
    }
}
