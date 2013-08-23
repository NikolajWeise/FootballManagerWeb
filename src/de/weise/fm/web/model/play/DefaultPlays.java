package de.weise.fm.web.model.play;

/**
 * Class with pre-defined plays for pre-defined formations.
 */
public class DefaultPlays {

    private static class PassPlay extends Play {
        public PassPlay(String name, Formation formation, PlayType playType) {
            super(name, formation, playType);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.C.equals(pw.getPosition())
                        || Position.RG.equals(pw.getPosition()) || Position.LG.equals(pw.getPosition())
                        || Position.RT.equals(pw.getPosition()) || Position.LT.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.PASS_BLOCK, pw.getFieldPosition().add(-1, 0)));
                }
            }
        }
    }

    private static class RunPlay extends Play {
        public RunPlay(String name, Formation formation, PlayType playType) {
            super(name, formation, playType);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.C.equals(pw.getPosition()) || Position.TE.equals(pw.getPosition())
                        || Position.RG.equals(pw.getPosition()) || Position.LG.equals(pw.getPosition())
                        || Position.RT.equals(pw.getPosition()) || Position.LT.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RUN_BLOCK, pw.getFieldPosition().add(1, 0)));
                }
            }
        }
    }

    public static class PassDefault extends PassPlay {
        public PassDefault(Formation formation) {
            super("Default Pass", formation, PlayType.PASS);

            boolean firstWR = true;
            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.PASS_BLOCK, pw.getFieldPosition().add(1, 1)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.PASS_BLOCK, pw.getFieldPosition().add(1, -1)));
                }
                if(Position.TE.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(10, 0)));
                }
                if(Position.WR.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(10, 0), firstWR));
                    firstWR = false;
                }
            }
        }
    }

    public static class RunDefault extends RunPlay {
        public RunDefault(Formation formation) {
            super("Default Run", formation, PlayType.RUN);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.LEAD_BLOCK, pw.getFieldPosition().add(7, 0)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.CRY_RUN, pw.getFieldPosition().add(10, 1)));
                }
                if(Position.WR.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RUN_BLOCK, pw.getFieldPosition().add(1, 0)));
                }
            }
        }
    }

    public static class IForm_QuickSlants extends PassPlay {
        public IForm_QuickSlants(Formation formation) {
            super("Quick Slants", formation, PlayType.PASS);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.PASS_BLOCK, pw.getFieldPosition().add(1, 1)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(0, -10)));
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(10, -10)));
                }
                if(Position.TE.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(1, 10)));
                }
                if(Position.WR.equals(pw.getPosition())) {
                    if(pw.getFieldPosition().isLeft()) {
                        addAction(pw, new Action(ActionType.RCV_SLANT, pw.getFieldPosition().add(2, 0), true));
                    } else {
                        addAction(pw, new Action(ActionType.RCV_SLANT, pw.getFieldPosition().add(2, 0)));
                    }
                }
            }
        }
    }

    public static class IForm_PaPowerO extends PassPlay {
        public IForm_PaPowerO(Formation formation) {
            super("PA Power O", formation, PlayType.PA);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(3, 10)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.PASS_BLOCK, pw.getFieldPosition().add(1, 3)));
                    addAction(pw, new Action(ActionType.PASS_BLOCK, pw.getFieldPosition().add(7, 3)));
                }
                if(Position.TE.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_FLAG, pw.getFieldPosition().add(2, 0)));
                }
                if(Position.WR.equals(pw.getPosition())) {
                    if(pw.getFieldPosition().isLeft()) {
                        addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(3, 2)));
                        addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(5, 20)));
                    } else {
                        addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(10, 1), true));
                    }
                }
            }
        }
    }

    public static class IForm_PowerO extends RunPlay {
        public IForm_PowerO(Formation formation) {
            super("Power O", formation, PlayType.RUN);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.LG.equals(pw.getPosition())) {
                    removeAction(pw);
                    addAction(pw, new Action(ActionType.RUN_BLOCK, pw.getFieldPosition().add(-1, 1)));
                    addAction(pw, new Action(ActionType.RUN_BLOCK, pw.getFieldPosition().add(-1, 10)));
                    addAction(pw, new Action(ActionType.RUN_BLOCK, pw.getFieldPosition().add(2, 11)));
                }
                if(Position.WR.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(10, 0)));
                }
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.LEAD_BLOCK, pw.getFieldPosition().add(1, 5)));
                    addAction(pw, new Action(ActionType.LEAD_BLOCK, pw.getFieldPosition().add(8, 8)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.CRY_RUN, pw.getFieldPosition().add(0, 1)));
                    addAction(pw, new Action(ActionType.CRY_RUN, pw.getFieldPosition().add(8, 7)));
                }
            }
        }
    }

    public static class Strong_PaGiantDig extends PassPlay {
        public Strong_PaGiantDig(Formation formation) {
            super("PA Giant Dig", formation, PlayType.PA);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.WR.equals(pw.getPosition())) {
                    if(pw.getFieldPosition().isLeft()) {
                        addAction(pw, new Action(ActionType.RCV_SLANT, pw.getFieldPosition().add(10, 0)));
                    } else {
                        addAction(pw, new Action(ActionType.RCV_IN, pw.getFieldPosition().add(10, 0), true));
                    }
                }
                if(Position.TE.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(3, -1)));
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(4, -15)));
                }
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.PASS_BLOCK, pw.getFieldPosition().add(0, 3)));
                    addAction(pw, new Action(ActionType.PASS_BLOCK, pw.getFieldPosition().add(2, 7)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(10, 0)));
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(10, 15)));
                }
            }
        }
    }

    public static class Strong_PaGiantSmash extends PassPlay {
        public Strong_PaGiantSmash(Formation formation) {
            super("PA Giant Smash", formation, PlayType.PA);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.WR.equals(pw.getPosition())) {
                    if(pw.getFieldPosition().isLeft()) {
                        addAction(pw, new Action(ActionType.RCV_IN, pw.getFieldPosition().add(7, 0), true));
                    } else {
                        addAction(pw, new Action(ActionType.RCV_HOOK, pw.getFieldPosition().add(5, 0)));
                    }
                }
                if(Position.TE.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_FLAG, pw.getFieldPosition().add(7, 0)));
                }
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_OUT, pw.getFieldPosition().add(7, 10)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_HOOK, pw.getFieldPosition().add(12, 0)));
                }
            }
        }
    }

    public static class Strong_HbBlast extends RunPlay {
        public Strong_HbBlast(Formation formation) {
            super("HB Blast", formation, PlayType.RUN);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.WR.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(10, 0)));
                }
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.LEAD_BLOCK, pw.getFieldPosition().add(5, 2)));
                    addAction(pw, new Action(ActionType.LEAD_BLOCK, pw.getFieldPosition().add(8, 2)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.CRY_RUN, pw.getFieldPosition().add(7, 5)));
                    addAction(pw, new Action(ActionType.CRY_RUN, pw.getFieldPosition().add(14, 5)));
                }
            }
        }
    }

    public static class Weak_PaBootSlide extends PassPlay {
        public Weak_PaBootSlide(Formation formation) {
            super("PA Boot Slide", formation, PlayType.PA);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.WR.equals(pw.getPosition())) {
                    if(pw.getFieldPosition().isLeft()) {
                        addAction(pw, new Action(ActionType.RCV_IN, pw.getFieldPosition().add(6, 6)));
                    } else {
                        addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(10, 0)));
                    }
                }
                if(Position.TE.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_FLAG, pw.getFieldPosition().add(5, 0), true));
                }
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(3, 10)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.PASS_BLOCK, pw.getFieldPosition().add(5, -2)));
                    addAction(pw, new Action(ActionType.PASS_BLOCK, pw.getFieldPosition().add(8, -5)));
                }
            }
        }
    }

    public static class Weak_PaGiantsYCross extends PassPlay {
        public Weak_PaGiantsYCross(Formation formation) {
            super("PA Giants Y Cross", formation, PlayType.PA);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.WR.equals(pw.getPosition())) {
                    if(pw.getFieldPosition().isLeft()) {
                        addAction(pw, new Action(ActionType.RCV_SLANT, pw.getFieldPosition().add(8, 0)));
                    } else {
                        addAction(pw, new Action(ActionType.RCV_IN, pw.getFieldPosition().add(10, 0)));
                    }
                }
                if(Position.TE.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(2, -1), true));
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(7, -20), true));
                }
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_RUN, pw.getFieldPosition().add(3, -10)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RCV_HOOK, pw.getFieldPosition().add(12, 0)));
                }
            }
        }
    }

    public static class Weak_HbGut extends RunPlay {
        public Weak_HbGut(Formation formation) {
            super("HB Gut", formation, PlayType.RUN);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.WR.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.RUN_BLOCK, pw.getFieldPosition().add(1, 0)));
                }
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.LEAD_BLOCK, pw.getFieldPosition().add(5, -1)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.CRY_RUN, pw.getFieldPosition().add(12, -2)));
                }
            }
        }
    }

    public static class GoalLine_HbDive extends RunPlay {
        public GoalLine_HbDive(Formation formation) {
            super("HB Dive", formation, PlayType.RUN);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.LEAD_BLOCK, pw.getFieldPosition().add(5, 1)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.CRY_RUN, pw.getFieldPosition().add(12, 2)));
                }
            }
        }
    }

    public static class GoalLine_FbDive extends RunPlay {
        public GoalLine_FbDive(Formation formation) {
            super("FB Dive", formation, PlayType.RUN);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.FB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.CRY_RUN, pw.getFieldPosition().add(8, -1)));
                }
                if(Position.HB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.LEAD_BLOCK, pw.getFieldPosition().add(2, 10)));
                }
            }
        }
    }

    /**
     * Class for creating a default defense play.
     * <p>
     * This creates blitz actions for every DL and man-to-man coverage for all other players.
     */
    public static class DefensePlay extends Play {
        public DefensePlay(String name, Formation formation) {
            super(name, formation, PlayType.DEFENSE);

            for(PositionWrapper pw : formation.getPositions()) {
                if(pw.getPosition().isDefLine()) {
                    addAction(pw, new Action(ActionType.BLITZ, pw.getFieldPosition().add(-3, 0)));
                } else {
                    addAction(pw, new Action(ActionType.MAN, pw.getFieldPosition().add(1, 0)));
                }
            }
        }
    }

    public static class Def_2ManUnder extends DefensePlay {
        public Def_2ManUnder(Formation formation) {
            super("2 Man Under", formation);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.SS.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(30, -10)));
                }
                if(Position.FS.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(30, 10)));
                }
            }
        }
    }

    public static class Def_EngageEight extends DefensePlay {
        public Def_EngageEight(Formation formation) {
            super("Engage Eight", formation);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.FS.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(30, 0)));
                }
                if(Position.CB.equals(pw.getPosition())) {
                    if(pw.getFieldPosition().isLeft()) {
                        addAction(pw, new Action(ActionType.ZONE, new FieldPosition(30, -15)));
                    } else {
                        addAction(pw, new Action(ActionType.ZONE, new FieldPosition(30, 15)));
                    }
                }
                if(Position.SS.equals(pw.getPosition()) || pw.getPosition().isLinebacker()) {
                    addAction(pw, new Action(ActionType.BLITZ, pw.getFieldPosition().add(-5, 0)));
                }
            }
        }
    }

    public static class Def_OlbFireMan extends DefensePlay {
        public Def_OlbFireMan(Formation formation) {
            super("OLB Fire Man", formation);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.LLB.equals(pw.getPosition()) || Position.RLB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.BLITZ, pw.getFieldPosition().add(-5, 0)));
                }
            }
        }
    }

    public static class Def_FreeFire extends DefensePlay {
        public Def_FreeFire(Formation formation) {
            super("Free Fire", formation);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.MLB.equals(pw.getPosition()) || Position.RLB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.BLITZ, pw.getFieldPosition().add(-5, 0)));
                }
            }
        }
    }

    public static class Def_ThunderSmoke extends DefensePlay {
        public Def_ThunderSmoke(Formation formation) {
            super("Thunder Smoke", formation);

            for(PositionWrapper pw : formation.getPositions()) {
                if(pw.getPosition().isLinebacker()) {
                    addAction(pw, new Action(ActionType.BLITZ, pw.getFieldPosition().add(-5, 0)));
                }
            }
        }
    }

    public static class Def_SafetyBlitz extends DefensePlay {
        public Def_SafetyBlitz(Formation formation) {
            super("Safety Blitz", formation);

            for(PositionWrapper pw : formation.getPositions()) {
                if(pw.getPosition().isSafety()) {
                    addAction(pw, new Action(ActionType.BLITZ, pw.getFieldPosition().add(-10, 0)));
                }
            }
        }
    }

    public static class Def_Cover1 extends DefensePlay {
        public Def_Cover1(Formation formation) {
            super("Cover 1", formation);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.MLB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(10, 0)));
                }
                if(Position.FS.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(30, 0)));
                }
            }
        }
    }

    public static class Def_Cover2 extends DefensePlay {
        public Def_Cover2(Formation formation) {
            super("Cover 2", formation);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.DT.equals(pw.getPosition())) {
                    removeAction(pw);
                    if(pw.getFieldPosition().isLeft()) {
                        addAction(pw, new Action(ActionType.BLITZ, pw.getFieldPosition().add(-3, 4)));
                    } else {
                        addAction(pw, new Action(ActionType.BLITZ, pw.getFieldPosition().add(-3, -4)));
                    }
                }
                if(Position.MLB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(10, 0)));
                }
                if(Position.RLB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(10, 15)));
                }
                if(Position.LLB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(10, -15)));
                }
                if(Position.SS.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(30, -10)));
                }
                if(Position.FS.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(30, 10)));
                }
                if(Position.CB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, pw.getFieldPosition().add(-1, 0)));
                }
            }
        }
    }

    public static class Def_Cover3 extends DefensePlay {
        public Def_Cover3(Formation formation) {
            super("Cover 3", formation);

            for(PositionWrapper pw : formation.getPositions()) {
                if(Position.DT.equals(pw.getPosition())) {
                    removeAction(pw);
                    if(pw.getFieldPosition().isLeft()) {
                        addAction(pw, new Action(ActionType.BLITZ, pw.getFieldPosition().add(-3, 4)));
                    } else {
                        addAction(pw, new Action(ActionType.BLITZ, pw.getFieldPosition().add(-3, -4)));
                    }
                }
                if(Position.MLB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(10, 10)));
                }
                if(Position.RLB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(0, 20)));
                }
                if(Position.LLB.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(10, -10)));
                }
                if(Position.SS.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(0, -20)));
                }
                if(Position.FS.equals(pw.getPosition())) {
                    addAction(pw, new Action(ActionType.ZONE, new FieldPosition(30, 0)));
                }
                if(Position.CB.equals(pw.getPosition())) {
                    if(pw.getFieldPosition().isLeft()) {
                        addAction(pw, new Action(ActionType.ZONE, new FieldPosition(30, -15)));
                    } else {
                        addAction(pw, new Action(ActionType.ZONE, new FieldPosition(30, 15)));
                    }
                }
            }
        }
    }
}
