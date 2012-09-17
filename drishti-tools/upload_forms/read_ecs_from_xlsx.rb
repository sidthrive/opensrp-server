require 'roo'
require 'csv'
require 'erb'
require 'guid'
require './lib/care_import_row.rb'
require './lib/commcare.rb'
require './lib/village.rb'

class ECs
  def initialize xlsx_filename
    @ecs = []

    read_from xlsx_filename
  end

  def ecs
    @ecs.collect {|ec| ec.to_hash}
  end

  private
  def read_from xlsx_filename
    filename = "#{Random.rand(9999999)}_EC_register.csv"

    begin
      spreadsheet = Excelx.new "examples/Munjanhalli.xlsx", nil, :ignore
      spreadsheet.to_csv filename, "EC register"

      CSV.foreach(filename, { :headers => true }) do |csv_row|
        ec = Row.new csv_row

        ec.convert_value "Registration date", :empty => Date.today.to_s
        ec.convert_value "House Number ", :empty => "111111"
        ec.convert_value "EC Number ", :empty => "111111"
        ec.convert_value "Wife Name", :empty => "Wife Unknown"
        ec.convert_value "Wife Age", :empty => "20"
        ec.convert_value "Husband Name", :empty => "Husband Unknown"
        ec.convert_value "Number of Abortion", :empty => "0"
        ec.convert_value "Number of Still Birth", :empty => "0"
        ec.convert_value "Number of Living Children", :empty => "0"
        ec.convert_value "DOB of youngest child ", :empty => Date.today.to_s
        ec.convert_value "Status of EC Woman [Permanent/ Continuting/Discontinued]", :empty => "Continuting"
        ec.convert_value "Acceptance Date", :empty => Date.today.to_s
        ec.convert_value "Pregnancy [Yes/No]", :empty => "No"
        ec.convert_value "if Yes LMP date", :empty => ""
        ec.convert_value "Thayi Card Number", :empty => "1234567"

        ec.convert_value "Village Code",
          "29230030060" => Village.new("bherya", "munjanahalli", "munjanahalli"),
          "29230030061" => Village.new("bherya", "munjanahalli", "chikkabheriya"),
          "29230030058" => Village.new("bherya", "munjanahalli", "kaval_hosur"),
          :empty        => Village.new("bherya", "munjanahalli", "munjanahalli"),
          :default      => Village.new("bherya", "munjanahalli", "munjanahalli")

        ec.convert_value "FP Method",
          "OP"             => "ocp",
          "IUD"            => "iud",
          "Condom"         => "condom",
          "Sterilization"  => "female_sterilization",
          :empty           => "none",
          :default         => "none"

        ec.add_field "Case ID", Guid.new.to_s
        ec.add_field "Instance ID", Guid.new.to_s

        @ecs << ec
      end
    ensure
      FileUtils.rm_f filename
    end
  end
end
