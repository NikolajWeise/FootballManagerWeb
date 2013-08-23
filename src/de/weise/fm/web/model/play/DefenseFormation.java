package de.weise.fm.web.model.play;

public class DefenseFormation extends Formation {

    public DefenseFormation(String name) {
        super(name);
        createDefault();
    }

    protected void createDefault() {
        addPosition(Position.DT, new FieldPosition(1, 2));
        addPosition(Position.DT, new FieldPosition(1, -2));
        addPosition(Position.DE, new FieldPosition(1, 6));
        addPosition(Position.DE, new FieldPosition(1, -6));
        addPosition(Position.MLB, new FieldPosition(5, 0));
        addPosition(Position.RLB, new FieldPosition(4, 5));
        addPosition(Position.LLB, new FieldPosition(4, -5));
        addPosition(Position.CB, FieldPosition.CB_RIGHT);
        addPosition(Position.CB, FieldPosition.CB_LEFT);
        addPosition(Position.SS, FieldPosition.SS);
        addPosition(Position.FS, FieldPosition.FS);
    }

    @Override
    public boolean validate() {
        if(!super.validate()) {
            return false;
        }

        for(PositionWrapper pw : getPositions()) {
            int height = pw.getFieldPosition().getHeight();
            if(height < 1) { // player over LoS
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isOffenseFormation() {
        return false;
    }

    public static class Def_4_3 extends DefenseFormation {
        public Def_4_3() {
            super("4-3");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.DT, new FieldPosition(1, 2));
            addPosition(Position.DT, new FieldPosition(1, -2));
            addPosition(Position.DE, new FieldPosition(1, 6));
            addPosition(Position.DE, new FieldPosition(1, -6));
            addPosition(Position.MLB, new FieldPosition(5, 0));
            addPosition(Position.RLB, new FieldPosition(4, 5));
            addPosition(Position.LLB, new FieldPosition(4, -5));
            addPosition(Position.CB, FieldPosition.CB_RIGHT);
            addPosition(Position.CB, FieldPosition.CB_LEFT);
            addPosition(Position.SS, FieldPosition.SS);
            addPosition(Position.FS, FieldPosition.FS);
        }
    }

    public static class Def_4_3_Under extends Def_4_3 {
        public Def_4_3_Under() {
            setName("4-3 Under");
        }

        @Override
        protected void createDefault() {
            super.createDefault();
            movePosition(Position.MLB, new FieldPosition(5, -4));
            movePosition(Position.RLB, new FieldPosition(5, 1));
            movePosition(Position.LLB, new FieldPosition(1, -9));
        }
    }

    public static class Def_4_3_Over extends Def_4_3 {
        public Def_4_3_Over() {
            setName("4-3 Over");
        }

        @Override
        protected void createDefault() {
            super.createDefault();
            movePosition(Position.MLB, new FieldPosition(5, 4));
            movePosition(Position.RLB, new FieldPosition(1, 9));
            movePosition(Position.LLB, new FieldPosition(5, -1));
        }
    }

    public static class Def_3_4 extends DefenseFormation {
        public Def_3_4() {
            super("3-4");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.DT, new FieldPosition(1, 0));
            addPosition(Position.DE, new FieldPosition(1, 4));
            addPosition(Position.DE, new FieldPosition(1, -4));
            addPosition(Position.MLB, new FieldPosition(5, 3));
            addPosition(Position.MLB, new FieldPosition(5, -3));
            addPosition(Position.RLB, new FieldPosition(3, 7));
            addPosition(Position.LLB, new FieldPosition(3, -7));
            addPosition(Position.CB, FieldPosition.CB_RIGHT);
            addPosition(Position.CB, FieldPosition.CB_LEFT);
            addPosition(Position.SS, FieldPosition.SS);
            addPosition(Position.FS, FieldPosition.FS);
        }
    }

    public static class Def_3_4_Under extends DefenseFormation {
        public Def_3_4_Under() {
            super("3-4 Under");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.DT, new FieldPosition(1, 0));
            addPosition(Position.DE, new FieldPosition(1, 4));
            addPosition(Position.DE, new FieldPosition(1, -4));
            addPosition(Position.MLB, new FieldPosition(5, 0));
            addPosition(Position.MLB, new FieldPosition(5, -4));
            addPosition(Position.RLB, new FieldPosition(5, 4));
            addPosition(Position.LLB, new FieldPosition(1, -8));
            addPosition(Position.CB, FieldPosition.CB_RIGHT);
            addPosition(Position.CB, FieldPosition.CB_LEFT);
            addPosition(Position.SS, FieldPosition.SS);
            addPosition(Position.FS, FieldPosition.FS);
        }
    }

    public static class Def_3_4_Over extends DefenseFormation {
        public Def_3_4_Over() {
            super("3-4 Over");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.DT, new FieldPosition(1, 0));
            addPosition(Position.DE, new FieldPosition(1, 4));
            addPosition(Position.DE, new FieldPosition(1, -4));
            addPosition(Position.MLB, new FieldPosition(5, 0));
            addPosition(Position.MLB, new FieldPosition(5, 4));
            addPosition(Position.RLB, new FieldPosition(1, 8));
            addPosition(Position.LLB, new FieldPosition(5, -4));
            addPosition(Position.CB, FieldPosition.CB_RIGHT);
            addPosition(Position.CB, FieldPosition.CB_LEFT);
            addPosition(Position.SS, FieldPosition.SS);
            addPosition(Position.FS, FieldPosition.FS);
        }
    }

    public static class Nickel_3_3_5 extends DefenseFormation {
        public Nickel_3_3_5() {
            super("Nickel 3-3-5");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.DT, new FieldPosition(1, 0));
            addPosition(Position.DE, new FieldPosition(1, 4));
            addPosition(Position.DE, new FieldPosition(1, -4));
            addPosition(Position.MLB, new FieldPosition(5, 0));
            addPosition(Position.RLB, new FieldPosition(3, 7));
            addPosition(Position.LLB, new FieldPosition(3, -7));
            addPosition(Position.CB, new FieldPosition(1, 15));
            addPosition(Position.CB, FieldPosition.CB_RIGHT);
            addPosition(Position.CB, FieldPosition.CB_LEFT);
            addPosition(Position.SS, FieldPosition.SS);
            addPosition(Position.FS, FieldPosition.FS);
        }
    }

    public static class Dime extends DefenseFormation {
        public Dime() {
            super("Dime");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.DT, new FieldPosition(1, 2));
            addPosition(Position.DT, new FieldPosition(1, -2));
            addPosition(Position.DE, new FieldPosition(1, 6));
            addPosition(Position.DE, new FieldPosition(1, -6));
            addPosition(Position.MLB, new FieldPosition(5, 0));
            addPosition(Position.CB, new FieldPosition(1, -15));
            addPosition(Position.CB, new FieldPosition(1, 15));
            addPosition(Position.CB, FieldPosition.CB_RIGHT);
            addPosition(Position.CB, FieldPosition.CB_LEFT);
            addPosition(Position.SS, FieldPosition.SS);
            addPosition(Position.FS, FieldPosition.FS);
        }
    }

    public static class Quarter extends DefenseFormation {
        public Quarter() {
            super("Quarter");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.DT, new FieldPosition(1, 0));
            addPosition(Position.DE, new FieldPosition(1, 4));
            addPosition(Position.DE, new FieldPosition(1, -4));
            addPosition(Position.MLB, new FieldPosition(5, 0));
            addPosition(Position.CB, new FieldPosition(1, -15));
            addPosition(Position.CB, new FieldPosition(1, 15));
            addPosition(Position.CB, FieldPosition.CB_RIGHT);
            addPosition(Position.CB, FieldPosition.CB_LEFT);
            addPosition(Position.SS, FieldPosition.SS);
            addPosition(Position.SS, new FieldPosition(15, 5));
            addPosition(Position.FS, new FieldPosition(17, 0));
        }
    }

    public static class GoalLineDefense extends DefenseFormation {
        public GoalLineDefense() {
            super("Goal Line Defense");
        }

        @Override
        protected void createDefault() {
            addPosition(Position.DT, new FieldPosition(1, 0));
            addPosition(Position.DT, new FieldPosition(1, 3));
            addPosition(Position.DT, new FieldPosition(1, -3));
            addPosition(Position.DE, new FieldPosition(1, 6));
            addPosition(Position.DE, new FieldPosition(1, -6));
            addPosition(Position.MLB, new FieldPosition(5, 2));
            addPosition(Position.MLB, new FieldPosition(5, -2));
            addPosition(Position.RLB, new FieldPosition(4, 6));
            addPosition(Position.LLB, new FieldPosition(4, -6));
            addPosition(Position.CB, FieldPosition.CB_RIGHT);
            addPosition(Position.CB, FieldPosition.CB_LEFT);
        }
    }
}
