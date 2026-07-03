import os

# لیست الگوها و نام فایل‌ها/پوشه‌هایی که باید نادیده گرفته شوند
IGNORED_PATTERNS = [
    # dependencies
    'node_modules', '.pnp', '.yarn',
    # testing
    'coverage',
    # next.js
    '.next', 'out',
    # production
    'build', 'dist',
    # misc
    '.DS_Store', '*.pem',
    # debug
    'npm-debug.log', 'yarn-debug.log', 'yarn-error.log', '.pnpm-debug.log',
    # env files
    '.env', '.vercel',
    # typescript
    '*.tsbuildinfo', 'next-env.d.ts',
    # Python virtual environment
    '.venv', 'env', 'ENV', '*.venv', 'env.bak',
    # Python cache files
    '__pycache__', '*.pyc', '*.pyo', '*.pyd',
    # IDE and editor files
    '.vscode', '.idea', '*.sublime-project', '*.sublime-workspace',
    # Runtime files
    '*.log', '*.pid', '*.sock',
    # Database files
    '*.db', '*.sqlite',
    # Git (معمولاً در ساختار درختی نیازی به نمایش محتویات گیت نیست)
    '.git'
]


def should_ignore(name):
    """بررسی می‌کند که آیا نام فایل یا پوشه باید نادیده گرفته شود یا خیر"""
    # بررسی تطابق دقیق
    if name in IGNORED_PATTERNS:
        return True

    # بررسی تطابق با الگوهای دارای ستاره (Wildcard)
    for pattern in IGNORED_PATTERNS:
        if '*' in pattern:
            # تبدیل الگوی ساده به regex (مثلا *.log -> ^.*\.log$)
            regex_pattern = pattern.replace('.', r'\.').replace('*', '.*')
            import re
            if re.match(regex_pattern, name):
                return True
    return False


def build_tree(start_path, prefix="", output_file=None):
    """ساخت ساختار درختی و نوشتن در فایل"""
    try:
        contents = os.listdir(start_path)
    except PermissionError:
        if output_file:
            output_file.write(f"{prefix}[Permission Denied]\n")
        return

    # جدا کردن پوشه‌ها و فایل‌ها و مرتب‌سازی آن‌ها
    folders = []
    files = []
    for item in contents:
        if should_ignore(item):
            continue

        full_path = os.path.join(start_path, item)
        if os.path.isdir(full_path):
            folders.append(item)
        else:
            files.append(item)

    # مرتب‌سازی الفبایی
    folders.sort()
    files.sort()

    # ترکیب لیست‌ها برای نمایش (ابتدا پوشه‌ها سپس فایل‌ها)
    all_items = folders + files
    total_items = len(all_items)

    for index, item in enumerate(all_items):
        full_path = os.path.join(start_path, item)
        is_last = (index == total_items - 1)

        # انتخاب کاراکتر مناسب برای شاخه درختی
        connector = "└── " if is_last else "├── "
        extension = "    " if is_last else "│   "

        line = f"{prefix}{connector}{item}"
        print(line)  # نمایش در کنسول
        if output_file:
            output_file.write(line + "\n")

        # اگر پوشه بود، به صورت بازگشتی وارد آن شویم
        if os.path.isdir(full_path):
            build_tree(full_path, prefix + extension, output_file)


def main():
    # مسیر جاری که کد در آن اجرا می‌شود
    root_dir = os.getcwd()
    output_filename = "directory_tree.txt"

    print(f"در حال اسکن پوشه: {root_dir}")
    print(f"نادیده گرفتن فایل‌های اضافی طبق لیست شما...")

    with open(output_filename, "w", encoding="utf-8") as f:
        f.write(f"ساختار درختی پوشه: {root_dir}\n")
        f.write("=" * 40 + "\n")
        build_tree(root_dir, output_file=f)

    print(f"\nعملیات با موفقیت انجام شد.")
    print(f"نتیجه در فایل '{output_filename}' ذخیره گردید.")


if __name__ == "__main__":
    main()