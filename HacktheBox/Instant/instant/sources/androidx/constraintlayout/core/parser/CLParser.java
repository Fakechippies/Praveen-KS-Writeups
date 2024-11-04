package androidx.constraintlayout.core.parser;

import androidx.constraintlayout.widget.ConstraintLayout;

/* loaded from: classes.dex */
public class CLParser {
    static boolean DEBUG = false;
    private boolean hasComment = false;
    private int lineNumber;
    private String mContent;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public enum TYPE {
        UNKNOWN,
        OBJECT,
        ARRAY,
        NUMBER,
        STRING,
        KEY,
        TOKEN
    }

    public static CLObject parse(String str) throws CLParsingException {
        return new CLParser(str).parse();
    }

    public CLParser(String str) {
        this.mContent = str;
    }

    public CLObject parse() throws CLParsingException {
        char c;
        char[] charArray = this.mContent.toCharArray();
        int length = charArray.length;
        int i = 1;
        this.lineNumber = 1;
        boolean z = false;
        int i2 = 0;
        while (true) {
            if (i2 >= length) {
                i2 = -1;
                break;
            }
            char c2 = charArray[i2];
            if (c2 == '{') {
                break;
            }
            if (c2 == '\n') {
                this.lineNumber++;
            }
            i2++;
        }
        if (i2 == -1) {
            throw new CLParsingException("invalid json content", null);
        }
        CLObject allocate = CLObject.allocate(charArray);
        allocate.setLine(this.lineNumber);
        allocate.setStart(i2);
        int i3 = i2 + 1;
        CLElement cLElement = allocate;
        while (i3 < length) {
            char c3 = charArray[i3];
            if (c3 == '\n') {
                this.lineNumber += i;
            }
            if (this.hasComment) {
                if (c3 == '\n') {
                    this.hasComment = z;
                } else {
                    continue;
                    i3++;
                    i = 1;
                    z = false;
                }
            }
            if (cLElement == null) {
                break;
            }
            if (cLElement.isDone()) {
                cLElement = getNextJsonElement(i3, c3, cLElement, charArray);
            } else if (cLElement instanceof CLObject) {
                if (c3 == '}') {
                    cLElement.setEnd(i3 - 1);
                } else {
                    cLElement = getNextJsonElement(i3, c3, cLElement, charArray);
                }
            } else if (!(cLElement instanceof CLArray)) {
                boolean z2 = cLElement instanceof CLString;
                if (z2) {
                    if (charArray[(int) cLElement.start] == c3) {
                        cLElement.setStart(cLElement.start + 1);
                        cLElement.setEnd(i3 - 1);
                    }
                } else {
                    if (cLElement instanceof CLToken) {
                        CLToken cLToken = (CLToken) cLElement;
                        if (!cLToken.validate(c3, i3)) {
                            throw new CLParsingException("parsing incorrect token " + cLToken.content() + " at line " + this.lineNumber, cLToken);
                        }
                    }
                    if (((cLElement instanceof CLKey) || z2) && (((c = charArray[(int) cLElement.start]) == '\'' || c == '\"') && c == c3)) {
                        cLElement.setStart(cLElement.start + 1);
                        cLElement.setEnd(i3 - 1);
                    }
                    if (!cLElement.isDone() && (c3 == '}' || c3 == ']' || c3 == ',' || c3 == ' ' || c3 == '\t' || c3 == '\r' || c3 == '\n' || c3 == ':')) {
                        long j = i3 - 1;
                        cLElement.setEnd(j);
                        if (c3 == '}' || c3 == ']') {
                            cLElement = cLElement.getContainer();
                            cLElement.setEnd(j);
                            if (cLElement instanceof CLKey) {
                                cLElement = cLElement.getContainer();
                                cLElement.setEnd(j);
                            }
                        }
                    }
                }
            } else if (c3 == ']') {
                cLElement.setEnd(i3 - 1);
            } else {
                cLElement = getNextJsonElement(i3, c3, cLElement, charArray);
            }
            if (cLElement.isDone() && (!(cLElement instanceof CLKey) || ((CLKey) cLElement).mElements.size() > 0)) {
                cLElement = cLElement.getContainer();
            }
            i3++;
            i = 1;
            z = false;
        }
        while (cLElement != null && !cLElement.isDone()) {
            if (cLElement instanceof CLString) {
                cLElement.setStart(((int) cLElement.start) + 1);
            }
            cLElement.setEnd(length - 1);
            cLElement = cLElement.getContainer();
        }
        if (DEBUG) {
            System.out.println("Root: " + allocate.toJSON());
        }
        return allocate;
    }

    private CLElement getNextJsonElement(int i, char c, CLElement cLElement, char[] cArr) throws CLParsingException {
        if (c == '\t' || c == '\n' || c == '\r' || c == ' ') {
            return cLElement;
        }
        if (c == '\"' || c == '\'') {
            if (cLElement instanceof CLObject) {
                return createElement(cLElement, i, TYPE.KEY, true, cArr);
            }
            return createElement(cLElement, i, TYPE.STRING, true, cArr);
        }
        if (c == '[') {
            return createElement(cLElement, i, TYPE.ARRAY, true, cArr);
        }
        if (c != ']') {
            if (c == '{') {
                return createElement(cLElement, i, TYPE.OBJECT, true, cArr);
            }
            if (c != '}') {
                switch (c) {
                    case '+':
                    case '-':
                    case '.':
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE /* 48 */:
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_EDITOR_ABSOLUTEX /* 49 */:
                    case '2':
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_TAG /* 51 */:
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_BASELINE_TO_TOP_OF /* 52 */:
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_CONSTRAINT_BASELINE_TO_BOTTOM_OF /* 53 */:
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_MARGIN_BASELINE /* 54 */:
                    case ConstraintLayout.LayoutParams.Table.LAYOUT_GONE_MARGIN_BASELINE /* 55 */:
                    case '8':
                    case '9':
                        return createElement(cLElement, i, TYPE.NUMBER, true, cArr);
                    case ',':
                    case ':':
                        return cLElement;
                    case '/':
                        int i2 = i + 1;
                        if (i2 >= cArr.length || cArr[i2] != '/') {
                            return cLElement;
                        }
                        this.hasComment = true;
                        return cLElement;
                    default:
                        if ((cLElement instanceof CLContainer) && !(cLElement instanceof CLObject)) {
                            CLElement createElement = createElement(cLElement, i, TYPE.TOKEN, true, cArr);
                            CLToken cLToken = (CLToken) createElement;
                            if (cLToken.validate(c, i)) {
                                return createElement;
                            }
                            throw new CLParsingException("incorrect token <" + c + "> at line " + this.lineNumber, cLToken);
                        }
                        return createElement(cLElement, i, TYPE.KEY, true, cArr);
                }
            }
        }
        cLElement.setEnd(i - 1);
        CLElement container = cLElement.getContainer();
        container.setEnd(i);
        return container;
    }

    private CLElement createElement(CLElement cLElement, int i, TYPE type, boolean z, char[] cArr) {
        CLElement allocate;
        if (DEBUG) {
            System.out.println("CREATE " + type + " at " + cArr[i]);
        }
        switch (AnonymousClass1.$SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[type.ordinal()]) {
            case 1:
                allocate = CLObject.allocate(cArr);
                i++;
                break;
            case 2:
                allocate = CLArray.allocate(cArr);
                i++;
                break;
            case 3:
                allocate = CLString.allocate(cArr);
                break;
            case 4:
                allocate = CLNumber.allocate(cArr);
                break;
            case 5:
                allocate = CLKey.allocate(cArr);
                break;
            case 6:
                allocate = CLToken.allocate(cArr);
                break;
            default:
                allocate = null;
                break;
        }
        if (allocate == null) {
            return null;
        }
        allocate.setLine(this.lineNumber);
        if (z) {
            allocate.setStart(i);
        }
        if (cLElement instanceof CLContainer) {
            allocate.setContainer((CLContainer) cLElement);
        }
        return allocate;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: androidx.constraintlayout.core.parser.CLParser$1, reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE;

        static {
            int[] iArr = new int[TYPE.values().length];
            $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE = iArr;
            try {
                iArr[TYPE.OBJECT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.ARRAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.KEY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$parser$CLParser$TYPE[TYPE.TOKEN.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }
}
