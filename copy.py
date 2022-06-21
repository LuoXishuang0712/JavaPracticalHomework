if(__name__ == "__main__"):
    raw_file_name = input("请输入待复制的文件名:")
    file_name_format = input("请输入文件名格式:")
    file_number_range = [int(item) for item in input("请输入标号范围，以英文逗号分隔:").split(",")]
    raw_file = open(raw_file_name, "rb")
    raw_data = raw_file.read()
    raw_file.close()
    for i in range(file_number_range[0], file_number_range[1] + 1):
        write_file = open(file_name_format%(i), "wb")
        write_file.write(raw_data)
        write_file.close()